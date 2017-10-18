package com.virtlink.editorservices.documents

import com.google.common.cache.CacheBuilder
import com.google.common.cache.CacheLoader
import com.google.inject.Inject
import com.virtlink.editorservices.IDocument
import org.slf4j.LoggerFactory
import java.io.File


/**
 * Manages the content of opened documents.
 */
class DocumentContentManager @Inject constructor(
        private val documentContentFactory: IDocumentContentFactory
) : IDocumentContentManager {

    private val logger = LoggerFactory.getLogger(DocumentContentManager::class.java)

    /**
     * A map of currently opened documents.
     */
    private val openedDocuments: MutableMap<IDocument, IDocumentContent> = HashMap()

    override fun getLatestContent(document: IDocument): IDocumentContent? {
        return this.openedDocuments[document]
    }

    override fun openDocument(document: IDocument): IDocumentContent {
        return this.openedDocuments.compute(document, {d, c ->
            if (c != null) {
                logger.warn("$d: Already opened")
                c
            } else {
                val newContent = this.documentContentFactory.create(document)
                logger.info("$d: Opened")
                newContent
            }
        })!!
    }

    override fun closeDocument(document: IDocument) {
        this.openedDocuments.compute(document, {d, c ->
            if (c == null) {
                logger.warn("$d: Already closed")
                null
            } else {
                logger.info("$d: Closed")
                null
            }
        })
    }

    override fun updateDocument(document: IDocument, content: IDocumentContent) {
        this.openedDocuments.compute(document, { d, c ->
            if (c == null) {
                logger.warn("$d: Update ignored, document not opened")
                null
            } else {
                logger.debug("$d: Update document with new content:\n$content")
                content
            }
        })
    }

}