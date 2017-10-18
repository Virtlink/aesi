package com.virtlink.editorservices.documents

import com.virtlink.editorservices.IDocument
import com.virtlink.editorservices.IProject
import com.virtlink.editorservices.Project
import org.slf4j.LoggerFactory
import java.net.URI
import java.util.concurrent.ConcurrentHashMap

/**
 * Manages the documents of a project.
 */
open class DocumentManager(private val project: IProject): IDocumentManager {

    private val logger = LoggerFactory.getLogger(DocumentManager::class.java)

    private val documentMap = ConcurrentHashMap<URI, IDocument>()

    override fun getDocument(uri: URI): IDocument {
        return this.documentMap.computeIfAbsent(uri, {
            val document = createDocument(uri)
            logger.info("$document: Unknown document discovered.")
            document
        })
    }

    protected open fun createDocument(uri: URI): IDocument {
        return Document(this.project, uri)
    }
}