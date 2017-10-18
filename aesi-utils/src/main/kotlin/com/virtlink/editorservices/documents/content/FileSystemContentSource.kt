package com.virtlink.editorservices.documents.content

import com.virtlink.editorservices.IDocument
import java.io.File
import java.nio.charset.Charset
import java.util.concurrent.locks.ReentrantReadWriteLock
import kotlin.concurrent.read
import kotlin.concurrent.write

/**
 * Content source for a document in the local file system.
 */
class FileSystemContentSource(document: IDocument)
    : ContentSource(document) {

    override fun retrieveContent(): IDocumentContent {
        val text = getFile().readText(getCharset())
        return TextContent(text)
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

}