package com.virtlink.pie

import com.virtlink.editorservices.IProject
import mb.pie.runtime.core.BuildManager

/**
 * Manages the build managers for the projects.
 */
interface IBuildManagerProvider {
    /**
     * Gets the build manager for the specified project.
     *
     * @param project The project.
     * @return The build manager.
     */
    fun getBuildManager(project: IProject): BuildManager
}
