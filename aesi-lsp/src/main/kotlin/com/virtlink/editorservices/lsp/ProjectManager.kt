package com.virtlink.editorservices.lsp

import com.google.inject.Inject
import com.virtlink.editorservices.Project
import com.virtlink.editorservices.documents.IDocumentManager
import com.virtlink.editorservices.documents.IDocumentManagerFactory
import org.slf4j.LoggerFactory
import java.net.URI
import java.util.concurrent.ConcurrentHashMap

/**
 * Manages the projects.
 */
class ProjectManager @Inject constructor(private val documentManagerFactory: IDocumentManagerFactory) {

    private val logger = LoggerFactory.getLogger(ProjectManager::class.java)

    private val projectMap = ConcurrentHashMap<URI, ProjectInfo>()

    private var project: Project? = null

    private data class ProjectInfo(val project: Project, val documents: IDocumentManager)

    fun getProject(): Project {
        return this.project ?: throw IllegalStateException("No opened project.")
    }

    fun openProject(uri: URI) {
        val projectInfo = projectMap.computeIfAbsent(uri, {
            val project = Project(it)
            ProjectInfo(project, documentManagerFactory.create(project))
        })
        this.project = projectInfo.project
    }

    fun closeProject(uri: URI) {
        this.project = null
    }

    fun getDocuments(project: Project): IDocumentManager
            = getDocuments(project.uri)

    fun getDocuments(uri: URI): IDocumentManager {
        val projectInfo = projectMap[uri] ?: throw IllegalArgumentException("Unknown project with URI $uri")
        return projectInfo.documents
    }
}