package org.metaborg.paplj

import com.intellij.openapi.project.Project
import com.virtlink.editorservices.IProject

/**
 * Manages the PAPLJ projects.
 */
object PapljProjectManager {

    fun toAesiProject(project: Project): IProject {
        return AesiProject(project)
    }

    private data class AesiProject(val project: Project): IProject

}