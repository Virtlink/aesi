package com.virtlink.editorservices.lsp

import com.google.inject.Inject
import com.virtlink.editorservices.Project
import com.virtlink.editorservices.documents.IDocumentManagerFactory
import org.slf4j.LoggerFactory
import java.net.URI
import java.util.concurrent.ConcurrentHashMap

/**
 * Manages the projects.
 */
class ProjectManager @Inject constructor(private val documentManagerFactory: IDocumentManagerFactory) {

    private val logger = LoggerFactory.getLogger(ProjectManager::class.java)

    private val projectMap = ConcurrentHashMap<URI, Project>()

    private var project: Project? = null

    fun getProject(): Project {
        return this.project ?: throw IllegalStateException("No opened project.")
    }

    fun openProject(uri: URI) {
        val project = projectMap.computeIfAbsent(uri, { Project(it, documentManagerFactory) })
        this.project = project
    }

    fun closeProject(uri: URI) {
        this.project = null
    }
}