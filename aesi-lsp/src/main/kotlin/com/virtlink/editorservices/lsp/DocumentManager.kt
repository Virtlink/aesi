package com.virtlink.editorservices.lsp

import com.google.inject.Inject
import com.google.inject.assistedinject.Assisted
import com.virtlink.logging.logger
import java.net.URI
import java.util.concurrent.ConcurrentHashMap

/**
 * Manages the documents in a project.
 */
class DocumentManager @Inject constructor(@Assisted private val project: Project) {

    @Suppress("PrivatePropertyName")
    private val LOG by logger()

    private val documentMap = ConcurrentHashMap<URI, DocumentInfo>()

    private data class DocumentInfo(val document: Document)

    /**
     * Gets the document with the specified URI.
     *
     * @param uri The document's URI.
     * @return The document.
     */
    fun getDocument(uri: URI): Document
            = getDocumentInfo(uri).document

    /**
     * Gets the info for the document with the specified URI.
     *
     * @param uri The document's URI.
     * @return The document's info.
     */
    private fun getDocumentInfo(uri: URI): DocumentInfo {
        return this.documentMap.getOrPut(uri, {
            DocumentInfo(Document(uri, this.project))
        })
    }

}