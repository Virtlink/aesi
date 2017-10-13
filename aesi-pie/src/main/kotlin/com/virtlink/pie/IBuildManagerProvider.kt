package com.virtlink.pie

import com.google.inject.Inject
import com.google.inject.Provider
import com.virtlink.editorservices.IProject
import mb.pie.runtime.core.BuildManager
import mb.pie.runtime.core.BuildManagerFactory
import mb.pie.runtime.core.impl.BuildCache
import mb.pie.runtime.core.impl.store.LMDBBuildStoreFactory
import mb.vfs.path.PPath
import mb.vfs.path.PathSrv
import java.util.concurrent.ConcurrentHashMap

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
