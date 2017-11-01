package com.virtlink.pie

import mb.pie.runtime.core.BuildManager
import java.net.URI

/**
 * Manages the build managers for the projects.
 */
interface IBuildManagerProvider {
    /**
     * Gets the build manager for the specified project.
     *
     * @param projectUri The project's URI.
     * @return The build manager.
     */
    fun getBuildManager(projectUri: URI): BuildManager
}
