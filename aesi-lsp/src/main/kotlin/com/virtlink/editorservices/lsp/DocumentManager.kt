package com.virtlink.editorservices.lsp

import com.virtlink.editorservices.IDocument
import org.eclipse.lsp4j.TextDocumentIdentifier
import org.slf4j.LoggerFactory
import java.io.File
import java.net.URI
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap

/**
 * Manages the documents.
 */
class DocumentManager {

    private var logger = LoggerFactory.getLogger(DocumentManager::class.java)

    private val documentMap = ConcurrentHashMap<URI, ILspDocument>()

    fun getDocument(uri: URI): ILspDocument {
        return this.documentMap.computeIfAbsent(uri, {
            logger.info("Unknown document discovered: $it")
            RealDocument(it)
        })
    }

    fun openDocument(uri: URI, text: String?) {
        this.documentMap.compute(uri, { key, doc ->
            if (doc is VirtualDocument) {
                logger.warn("Document already opened: $key")
                doc
            } else {
                if (doc == null) {
                    logger.info("Unknown document opened: $key")
                }
                // Get the given text, or the text of the document on disk if no text was given.
                val documentText = text ?: readTextFromDisk(key)
                // Create a VirtualDocument to represent the opened document,
                // and update it with the document text.
                val newDoc = VirtualDocument(key)
                newDoc.updateAll(documentText)
                logger.info("Opened document: $key")
                newDoc
            }
        })
    }

    fun closeDocument(uri: URI) {
        this.documentMap.compute(uri, { key, doc ->
            if (doc !is VirtualDocument) {
                logger.warn("Document already closed: $key")
                doc
            } else {
                if (doc == null) {
                    logger.warn("Unknown document closed: $key")
                }
                val newDoc = RealDocument(key)
                logger.info("Closed document: $key")
                newDoc
            }
        })
    }

    /**
     * Updates the document to reflect the specified change.
     *
     * @param offset The zero-based offset of the start of the change.
     * @param length The number of changed characters (including newlines), which may be 0 when text was only inserted.
     * @param newText The next text of the specified range, which may be an empty string when text was only removed.
     */
    fun updateDocument(uri: URI, offset: Int, length: Int, newText: String) {
        val doc = this.documentMap[uri]

        if (doc == null) {
            logger.warn("Update ignored, document was unknown: $uri")
        } else if (doc !is VirtualDocument) {
            logger.warn("Update ignored, document was not opened: $uri")
        } else {
            doc.update(offset, length, newText)
            logger.trace("Updated $offset-${offset+length} ($length) to be ${newText.length} characters in document: $uri")
        }
    }

    companion object {
        internal fun readTextFromDisk(uri: URI): String {
            // FIXME: Character set is probably not always UTF8,
            // but we have no way of knowing the correct character set.
            // In any case, this should be the same character set as used
            // by the editor, as otherwise the character to offset conversions
            // and line-ending detections would not work correctly.
            // So UTF8 is our best guess here.
            return File(uri).readText(Charsets.UTF_8)
        }
    }
}