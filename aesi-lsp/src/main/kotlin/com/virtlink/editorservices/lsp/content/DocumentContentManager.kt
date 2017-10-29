package com.virtlink.editorservices.lsp.content

import com.google.inject.Inject
import com.virtlink.editorservices.resources.IContent
import com.virtlink.logging.logger
import java.net.URI
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentSkipListSet

/**
 * Manages the content of the documents.
 */
class DocumentContentManager @Inject constructor(
        private val remoteContentSource: RemoteContentSource) {

    @Suppress("PrivatePropertyName")
    private val LOG by logger()

    /** Map of documents and their corresponding content source. */
    private val openDocuments: MutableSet<URI> = mutableSetOf()

    fun getLatestContent(documentUri: URI): IContent? {
        if (!this.openDocuments.contains(documentUri)) return null
        return this.remoteContentSource.getLatest(documentUri)
    }

    /**
     * Opens the document, giving it the specified text and version.
     *
     * @param documentUri The document AESI URI to open.
     * @param text The text of the opened document.
     * @param version The version of the opened document.
     */
    fun open(documentUri: URI, text: String, stamp: Long) {
        val added = this.openDocuments.add(documentUri)
        if (added) {
            this.remoteContentSource.attach(documentUri, text, stamp)
            LOG.info("$documentUri: Document opened")
        } else {
            LOG.warn("$documentUri: Document already opened")
        }
    }

    /**
     * Closes the document.
     *
     * @param documentUri The document AESI URI to close.
     */
    fun close(documentUri: URI) {
        val removed = this.openDocuments.remove(documentUri)
        if (removed) {
            this.remoteContentSource.detach(documentUri)
            LOG.info("$documentUri: Document closed")
        } else {
            LOG.warn("$documentUri: Document already closed")
        }
    }
}