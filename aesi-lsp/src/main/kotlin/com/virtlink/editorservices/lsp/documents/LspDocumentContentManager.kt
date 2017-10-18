package com.virtlink.editorservices.lsp.documents

import com.google.common.cache.CacheBuilder
import com.google.common.cache.CacheLoader
import com.virtlink.editorservices.IDocument
import com.virtlink.editorservices.documents.content.*
import java.util.concurrent.locks.ReentrantReadWriteLock
import kotlin.concurrent.read

class LspDocumentContentManager {

    private var lock = ReentrantReadWriteLock()

    /**
     * A map of opened documents.
     */
    private val openedDocuments: MutableMap<IDocument, VirtualContentSource> = mutableMapOf()

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
     * @param version The version of the document that is currently opened.
     * @param text The text of the opened document.
     */
    fun openDocument(document: IDocument, version: Int, text: String) {

    }

    /**
     * Closes the specified document.
     *
     * If the document is already closed, nothing happens.
     *
     * @param document The document being closed.
     */
    fun closeDocument(document: IDocument)

    /**
     * Updates the specified document.
     *
     * @param document The document being updated.
     * @param changes The changes to the document's content, in terms of the current content.
     * @param newVersion The new version of the document's content.
     */
    fun updateDocument(document: IDocument, changes: List<DocumentChange>, newVersion: Int)

    private fun createContentSource(document: IDocument): IContentSource {

    }

    private fun createOpenContentSource(document: IDocument, version: Int, text: String): VirtualContentSource {
        return RemoteContentSource(document, version)
    }
}