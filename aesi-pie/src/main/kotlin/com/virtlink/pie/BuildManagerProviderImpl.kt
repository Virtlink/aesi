package com.virtlink.pie

import com.google.inject.Inject
import com.google.inject.Provider
import com.virtlink.editorservices.IProject
import com.virtlink.pie.codecompletion.PieCodeCompletionBuilder
import mb.pie.runtime.core.BuildManager
import mb.pie.runtime.core.BuildManagerFactory
import mb.pie.runtime.core.impl.BuildCache
import mb.pie.runtime.core.impl.store.BuildStore
import mb.pie.runtime.core.impl.store.InMemoryBuildStore
import mb.pie.runtime.core.impl.store.LMDBBuildStoreFactory
import mb.vfs.path.PPathImpl
import mb.vfs.path.PathSrv
import org.slf4j.LoggerFactory
import java.util.concurrent.ConcurrentHashMap

class BuildManagerProviderImpl @Inject constructor(
        private val pathSrv: PathSrv,
        private val buildManagerFactory: BuildManagerFactory,
        private val storeFactory: LMDBBuildStoreFactory,
        private val cacheFactory: Provider<BuildCache>)
    : IBuildManagerProvider {

    private val logger = LoggerFactory.getLogger(PieCodeCompletionBuilder::class.java)

    private val buildManagers = ConcurrentHashMap<IProject, BuildManager>()

    override fun getBuildManager(project: IProject): BuildManager {
        return buildManagers.getOrPut(project) {
            val store = createStore(project)
            val cache = cacheFactory.get()
            buildManagerFactory.create(store, cache)
        }
    }

    private fun createStore(project: IProject): BuildStore {
        val projectRootDir = PPathImpl(project.uri)
        val storeDir = projectRootDir.resolve(".pie")
        val localStoreDir = pathSrv.localPath(storeDir)
        val store: BuildStore
        if (localStoreDir != null) {
            store = storeFactory.create(localStoreDir)
            logger.debug("Created PIE LMDB store at $storeDir for project $project.")
        } else {
            store = InMemoryBuildStore()
            logger.warn("Could not create PIE LMDB store at $storeDir for project $project because it is not on the local filesystem. Using an in-memory store instead.")
        }
        return store
    }
}
