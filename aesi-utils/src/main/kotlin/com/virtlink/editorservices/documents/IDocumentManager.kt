package com.virtlink.editorservices.documents

import com.virtlink.editorservices.IDocument
import com.virtlink.editorservices.Project
import org.slf4j.LoggerFactory
import java.net.URI
import java.util.concurrent.ConcurrentHashMap

/**
 * Manages the documents of a project.
 */
interface IDocumentManager {

    fun getDocument(uri: URI): IDocument

}