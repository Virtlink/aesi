package com.virtlink.editorservices.lsp

import com.google.inject.Inject
import com.virtlink.logging.logger
import java.net.URI
import java.util.concurrent.locks.ReentrantReadWriteLock
import kotlin.concurrent.read
import kotlin.concurrent.write

/**
 * Manages the projects.
 */
class ProjectManager @Inject constructor(private val documentManagerFactory: IDocumentManagerFactory) {

    @Suppress("PrivatePropertyName")
    private val LOG by logger()

    /** Lock */
    private val lock = ReentrantReadWriteLock()

    private var openedProject: ProjectInfo? = null

    private data class ProjectInfo(val project: Project, val documents: DocumentManager)

    fun getProject(): Project
            = this.lock.read { getProjectInfo().project }

    private fun getProjectInfo(): ProjectInfo
            = this.lock.read { this.openedProject ?: throw IllegalStateException("No opened project.") }

    fun getDocuments(): DocumentManager
            = this.lock.read { getProjectInfo().documents }

    fun open(uri: URI) {
        this.lock.write {
            if (this.openedProject != null) {
                throw IllegalStateException("Another project is already open.")
            }

            val project = Project(uri)
            this.openedProject = ProjectInfo(project, documentManagerFactory.create(project))
            LOG.info("$project: Opened")
        }
    }

    fun close() {
        this.lock.write {
            val project = this.openedProject
            if (project == null) {
                LOG.warn("Project already closed.")
                return
            }
            this.openedProject = null
            LOG.info("$project: Closed")
        }
    }
}