package com.virtlink.editorservices.lsp.documents

import com.google.common.cache.CacheBuilder
import com.google.common.cache.CacheLoader
import com.virtlink.editorservices.IDocument
import com.virtlink.editorservices.documents.content.*
import org.slf4j.LoggerFactory
import java.util.concurrent.locks.ReentrantReadWriteLock
import kotlin.concurrent.read
import kotlin.concurrent.write

class LspDocumentContentManager {

    private val logger = LoggerFactory.getLogger(LspDocumentContentManager::class.java)

    private var lock = ReentrantReadWriteLock()

    /**
     * A map of opened documents.
     */
    private val openedDocuments: MutableMap<IDocument, VersionedContentSource> = mutableMapOf()

    /**
     * A cache of known documents.
     */
    private val documents = CacheBuilder.newBuilder()
        .maximumSize(1000)
        .build(object : CacheLoader<IDocument, IContentSource>() {
            override fun load(doc: IDocument): IContentSource {
                return createContentSource(doc)
            }
        })

    /**
     * Gets the content for the specified document.
     *
     * @param document The document.
     * @return The document's content.
     */
    fun getContent(document: IDocument): IDocumentContent {
        val source = getContentSource(document)
        return source.getLatestContent()
    }

    /**
     * Gets the content source of the specified document.
     */
    private fun getContentSource(document: IDocument): IContentSource {
        this.lock.read {
            return this.openedDocuments.getOrElse(document, { this.documents.get(document) })
        }
    }

    /**
     * Opens the specified document.
     *
     * If the document is already opened, nothing happens.
     *
     * @param document The document being opened.
     * @param text The text of the opened document.
     * @param version The version of the document that is currently opened.
     */
    fun openDocument(document: IDocument, text: String, version: Int) {
        val source = createOpenContentSource(document, text, version)
        this.lock.write {
            val oldSource = this.openedDocuments.put(document, source)
            if (oldSource != null) {
                logger.warn("$document: Already opened, replacing document")
            }
            this.documents.invalidate(document)
            logger.info("$document: Opened")
        }
    }

    /**
     * Closes the specified document.
     *
     * If the document is already closed, nothing happens.
     *
     * @param document The document being closed.
     */
    fun closeDocument(document: IDocument) {
        this.lock.write {
            val oldSource = this.openedDocuments.remove(document)
            if (oldSource == null) {
                logger.warn("$document: Already closed")
            } else {
                logger.info("$document: Closed")
            }
        }
    }

    /**
     * Updates the specified document.
     *
     * @param document The document being updated.
     * @param changes The changes to the document's content, in terms of the current content.
     * @param newVersion The new version of the document's content.
     */
    fun updateDocument(document: IDocument, changes: List<DocumentChange>, newVersion: Int) {
        this.lock.read {
            val source = this.openedDocuments[document]
            if (source == null) {
                logger.warn("$document: Not open, changes ignored")
                return
            }

            val content = source.update(changes, newVersion)
            if (content == null) {
                logger.warn("$document: Changes were not applied")
                return
            }
            logger.info("$document: Applied ${changes.size} changes")
            logger.trace("$document:\n${content?.text}")
        }
    }

    private fun createContentSource(document: IDocument): IContentSource {
        return FileSystemContentSource(document)
    }

    private fun createOpenContentSource(document: IDocument, text: String, version: Int): VersionedContentSource {
        return VersionedContentSource(document, text, version)
    }
}