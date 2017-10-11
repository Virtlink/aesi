package com.virtlink.editorservices.lsp

import org.slf4j.LoggerFactory
import java.net.URI
import java.util.concurrent.ConcurrentHashMap

/**
 * Manages the projects.
 */
class ProjectManager {

    private val logger = LoggerFactory.getLogger(ProjectManager::class.java)

    private val projectMap = ConcurrentHashMap<URI, Project>()

    private var project: Project? = null

    fun getProject(): Project {
        return this.project ?: throw IllegalStateException("No opened project.")
    }

    fun openProject(uri: URI) {
        val project = projectMap.computeIfAbsent(uri, { Project(it) })
        this.project = project
    }

    fun closeProject(uri: URI) {
        this.project = null
    }
}