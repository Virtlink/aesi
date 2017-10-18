package com.virtlink.editorservices.documents2

import com.google.common.cache.CacheLoader
import com.virtlink.editorservices.IDocument
import org.slf4j.LoggerFactory
import com.google.common.cache.CacheBuilder

abstract class DocumentContentManager: IDocumentContentManager {

    private val logger = LoggerFactory.getLogger(DocumentContentManager::class.java)

//    /**
//     * A map of sources for the currently opened documents.
//     */
//    private val openedDocuments: MutableMap<IDocument, IContentSource> = HashMap()

    /**
     * A cache of sources for known but unopened documents.
     */
    private val documents = CacheBuilder.newBuilder()
            .maximumSize(1000)
            .build(object : CacheLoader<IDocument, IContentSource>() {
                        override fun load(doc: IDocument): IContentSource {
                            return getContentSource(doc)
                        }
                    })

    override fun getContent(document: IDocument): IDocumentContent {
        val source = this.documents.get(document)
//        val source = this.openedDocuments.getOrElse(document, {
//            this.documents.get(document)
//        })
        return source.getLatestContent()
    }
//
//    override fun openDocument(document: IDocument) {
//        documents.
//    }
//
//    override fun closeDocument(document: IDocument) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }

    protected abstract fun getContentSource(document: IDocument): IContentSource
}