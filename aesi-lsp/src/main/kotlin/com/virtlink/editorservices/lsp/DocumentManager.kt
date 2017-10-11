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
class DocumentManager(private val project: Project) {

    private val logger = LoggerFactory.getLogger(DocumentManager::class.java)

    private val documentMap = ConcurrentHashMap<URI, ILspDocument>()

    fun getDocument(uri: URI): ILspDocument {
        return this.documentMap.computeIfAbsent(uri, {
            val doc = RealDocument(this.project, it)
            logger.info("${doc.relativeUri}: Unknown document discovered.")
            doc
        })
    }

    fun openDocument(uri: URI, text: String?) {
        this.documentMap.compute(uri, { key, doc ->
            if (doc is VirtualDocument) {
                logger.warn("${doc.relativeUri}: Document already opened.")
                doc
            } else {
                if (doc == null) {
                    logger.info("${this.project.uri.relativize(uri)}: Unknown document opened.")
                }
                // Get the given text, or the text of the document on disk if no text was given.
                val documentText = text ?: readTextFromDisk(key)
                // Create a VirtualDocument to represent the opened document,
                // and update it with the document text.
                val newDoc = VirtualDocument(this.project, key)
                newDoc.updateAll(documentText)
                logger.info("${newDoc.relativeUri}: Document opened.")
                newDoc
            }
        })
    }

    fun closeDocument(uri: URI) {
        this.documentMap.compute(uri, { key, doc ->
            if (doc is RealDocument) {
                logger.warn("${doc.relativeUri}: Document already closed.")
                doc
            } else {
                if (doc == null) {
                    logger.warn("${this.project.uri.relativize(uri)}: Unknown document closed.")
                }
                val newDoc = RealDocument(this.project, key)
                logger.info("${newDoc.relativeUri}: Document closed.")
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
            logger.warn("${this.project.uri.relativize(uri)}: Update ignored, document was unknown.")
        } else if (doc !is VirtualDocument) {
            logger.warn("${doc.relativeUri}: Update ignored, document was not opened.")
        } else {
            doc.update(offset, length, newText)
            logger.trace("${doc.relativeUri}: Updated $offset-${offset+length} ($length) to be ${newText.length} characters in document.")
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