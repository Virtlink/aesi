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

    override fun getContent(document: IDocument): IDocumentContent? {
        return this.openedDocuments[document]
    }

    override fun openDocument(document: IDocument): IDocumentContent {
        var content = getContent(document)
        if (content == null) {
            content = this.documentContentFactory.create(document)
            this.openedDocuments.put(document, content)
            logger.info("$document: Opened")
        } else {
            logger.warn("$document: Already opened")
        }
        return content
    }

    override fun closeDocument(document: IDocument) {
        val content = getContent(document)
        if (content != null) {
            this.openedDocuments.remove(document)
            logger.info("$document: Closed")
        } else {
            logger.warn("$document: Already closed")
        }
    }

}