package com.virtlink.editorservices.lsp.documents

import com.virtlink.editorservices.IDocument
import com.virtlink.editorservices.documents.content.DocumentChange
import com.virtlink.editorservices.documents.content.ContentSource
import com.virtlink.editorservices.documents.content.IDocumentContent
import com.virtlink.editorservices.documents.content.VirtualContent
import kotlin.concurrent.write

class VersionedContentSource(document: IDocument, text: String, version: Int)
    : ContentSource(document, VirtualContent(text)) {

    /**
     * The latest version of the content.
     */
    private var latestVersion: Int = version

    override fun getLatestContent(): VersionedContent {
        return VersionedContent(this.latestVersion, super.getLatestContent())
    }

    override fun invalidate() {
        // Does nothing.
    }

    override fun retrieveContent(): IDocumentContent {
        TODO("Not natively supported by LSP.")
        // We can implement a custom extension that gets the full document's content
        // such as the xcontent extension: https://github.com/sourcegraph/language-server-protocol/blob/master/extension-files.md
    }

    override fun update(changes: List<DocumentChange>): IDocumentContent? {
        throw UnsupportedOperationException()
    }

    /**
     * Applies updates to the specified virtual content.
     *
     * All changes must be specified in terms of the document content being changed.
     * Changes must not overlap, and be specified in the order they occur in the document.
     *
     * @param content The document content being changed.
     * @param changes The changes to apply to the content in parallel.
     * @param newVersion The new version of the content.
     * @return The resulting document content.
     */
    fun update(changes: List<DocumentChange>, newVersion: Int): VersionedContent? {
        this.lock.write {
            val content = super.update(changes)
            if (content != null) {
                this.latestVersion = newVersion
                return VersionedContent(newVersion, content)
            } else {
                return null
            }
        }
    }
}