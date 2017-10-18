package com.virtlink.editorservices.documents.content

import com.virtlink.editorservices.*
import java.util.concurrent.locks.ReentrantReadWriteLock
import kotlin.concurrent.read
import kotlin.concurrent.write

abstract class VirtualContentSource(override val document: IDocument) : IContentSource {

    private var latestContent: VirtualContent? = null

    private var lock = ReentrantReadWriteLock()

    companion object {
        val emptyContent = VirtualContent(listOf(Line(Offset(0), "")))
    }

    override fun invalidate() {
        this.lock.write {
            this.latestContent = null
        }
    }

    override fun getLatestContent(): IDocumentContent {
        this.lock.read {
            var content = this.latestContent
            if (content == null) {
                content = readFileContent()
                this.lock.write {
                    this.latestContent = content
                }
            }
            return content
        }
    }

    protected open fun readFileContent(): VirtualContent {
        val text = readText()
        return update(emptyContent, listOf(DocumentChange(Span(Offset(0), Offset(0)), text)))
    }

    /**
     * Reads the file's content.
     */
    abstract protected fun readText(): String

    /**
     * Applies updates to the specified virtual content.
     *
     * All changes must be specified in terms of the document content being changed.
     * Changes must not overlap, and be specified in the order they occur in the document.
     *
     * @param content The document content being changed.
     * @param changes The changes to apply to the content in parallel.
     */
    protected fun update(content: VirtualContent, changes: List<DocumentChange>): VirtualContent {
        val lines = content.lines.toMutableList()
        for (change in changes.asReversed()) {
            val start = content.getPosition(change.span.start)!!
            val end = content.getPosition(change.span.end)!!
            applyChange(lines, start, end, change.newText)
        }
        return VirtualContent(lines)
    }

}