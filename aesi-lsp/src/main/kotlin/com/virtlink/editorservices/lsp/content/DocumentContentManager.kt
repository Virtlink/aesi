package com.virtlink.editorservices.lsp.content

import com.google.inject.Inject
import com.virtlink.editorservices.IDocument
import com.virtlink.editorservices.content.IContent
import com.virtlink.editorservices.content.IContentManager
import com.virtlink.editorservices.content.IContentSource
import com.virtlink.logging.logger
import java.util.concurrent.ConcurrentHashMap

/**
 * Manages the content of the documents.
 */
class DocumentContentManager @Inject constructor(
        private val fileSystemContentSource: FileSystemContentSource,
        private val remoteContentSource: RemoteContentSource)
    : IContentManager {

    @Suppress("PrivatePropertyName")
    private val LOG by logger()

    /** Map of documents and their corresponding content source. */
    private val documents: ConcurrentHashMap<IDocument, IContentSource> = ConcurrentHashMap()

    override fun getLatestContent(document: IDocument): IContent
            = getContentSource(document).getLatest(document)

    /**
     * Gets the content source of the specified document.
     *
     * @param document The document.
     * @return The content source.
     */
    private fun getContentSource(document: IDocument): IContentSource
            = documents.getOrDefault(document, this.fileSystemContentSource)

    /**
     * Opens the document, giving it the specified text and version.
     *
     * @param document The document to open.
     * @param text The text of the opened document.
     * @param version The version of the opened document.
     */
    fun open(document: IDocument, text: String, version: Int) {
        this.documents.compute(document, { d, c ->
            when (c) {
                is RemoteContentSource -> {
                    LOG.warn("$d: Document already opened")
                    c
                }
                null -> {
                    val newSource = this.remoteContentSource
                    newSource.attach(document, text, version)
                    LOG.info("$d: Document opened")
                    newSource
                }
                else -> throw IllegalStateException("Document '$d' has unknown content source: $c")
            }
        })
    }

    /**
     * Closes the document.
     *
     * @param document The document to close.
     */
    fun close(document: IDocument) {
        this.documents.compute(document, { d, c ->
            when (c) {
                is RemoteContentSource -> {
                    c.detach(d)
                    LOG.info("$d: Document closed")
                    null
                }
                null -> {
                    LOG.warn("$d: Document already closed")
                    null
                }
                else -> throw IllegalStateException("Document '$d' has unknown content source: $c")
            }
        })
    }
}