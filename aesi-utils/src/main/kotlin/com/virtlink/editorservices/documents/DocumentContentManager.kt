package com.virtlink.editorservices.documents

import com.google.common.cache.CacheBuilder
import com.google.common.cache.CacheLoader
import com.google.inject.Inject
import com.virtlink.editorservices.IDocument
import org.slf4j.LoggerFactory
import java.io.File


/**
 * Manages the content of the documents.
 *
 * This class provides the most up-to-date content of a document.
 */
class DocumentContentManager @Inject constructor(
        private val documentContentFactory: IDocumentContentFactory
) {


    private val logger = LoggerFactory.getLogger(DocumentContentManager::class.java)

    /**
     * A map of currently opened documents.
     */
    private val openedDocuments: MutableMap<IDocument, IUpdatableDocumentContent> = HashMap()

    /**
     * A cache of documents, not currently opened.
     */
    private val cachedDocuments = CacheBuilder.newBuilder()
            .maximumSize(1000)
            .build(object : CacheLoader<IDocument, IDocumentContent>() {
                        override fun load(key: IDocument): IDocumentContent = documentContentFactory.create(key, false)
                    })

    /**
     * Gets the content for the specified document.
     */
    fun getContent(document: IDocument): IDocumentContent {
        return openedDocuments[document] ?: cachedDocuments.get(document)
    }

    /**
     * Opens the specified document.
     */
    fun openDocument(document: IDocument): IUpdatableDocumentContent {
        var content = this.openedDocuments[document]
        if (content == null) {
            val cachedContent = this.cachedDocuments.getIfPresent(document)
            this.cachedDocuments.invalidate(document)
            content = this.documentContentFactory.createUpdatable(document)
            this.openedDocuments.put(document, content)
            logger.info("$document: Opened")
        } else {
            logger.warn("$document: Already opened")
        }
        return content
    }

    /**
     * Closes the specified document.
     */
    fun closeDocument(document: IDocument) {
        val content = this.openedDocuments[document]
        if (content != null) {
            this.openedDocuments.remove(document)
            logger.info("$document: Closed")
        } else {
            logger.warn("$document: Already closed")
        }
    }

}