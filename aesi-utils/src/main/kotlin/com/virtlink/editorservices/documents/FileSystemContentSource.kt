package com.virtlink.editorservices.documents

import com.virtlink.editorservices.IDocument
import java.io.File
import java.io.FileInputStream
import java.io.InputStreamReader
import java.nio.charset.Charset
import java.util.concurrent.locks.ReentrantReadWriteLock
import kotlin.concurrent.read
import kotlin.concurrent.write

/**
 * Content source for a document in the local file system.
 */
class FileSystemContentSource(override val document: IDocument)
    : IContentSource {

    private var latestContent: IDocumentContent? = null

    private var lock = ReentrantReadWriteLock()

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

    private fun getFile(): File {
        // TODO: Error handling.
        // For example, when the URI does not point to a local file.
        return File(this.document.uri)
    }

    private fun getCharset(): Charset {
        // TODO: Determine character set.
        // For example, use the same character set as the editor
        // as otherwise line offsets might not match up.
        return Charsets.UTF_8
    }

    private fun readFileContent(): IDocumentContent {
        FileInputStream(getFile()).use {
            InputStreamReader(it, getCharset()).use {
                val text = it.readText()
                return TextContent(text)
            }
        }
    }

}