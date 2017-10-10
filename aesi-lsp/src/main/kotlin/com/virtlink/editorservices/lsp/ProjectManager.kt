package com.virtlink.editorservices.lsp

import com.virtlink.editorservices.IProject

/**
 * Manages the projects.
 */
class ProjectManager {
    fun getProject(): IProject {
        return Project()
    }
}