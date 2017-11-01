package com.virtlink.pie

import com.google.inject.Inject
import com.google.inject.Provider
import com.virtlink.dummy.DummyCodeCompletionBuilder
import mb.pie.runtime.core.BuildCache
import mb.pie.runtime.core.BuildManager
import mb.pie.runtime.core.BuildManagerFactory
import mb.pie.runtime.core.BuildStore
import mb.pie.runtime.core.impl.store.InMemoryBuildStore
import mb.pie.runtime.core.impl.store.LMDBBuildStoreFactory
import mb.vfs.path.PathSrv
import org.slf4j.LoggerFactory
import java.io.File
import java.net.URI
import java.util.concurrent.ConcurrentHashMap

class BuildManagerProviderImpl @Inject constructor(
        private val pathSrv: PathSrv,
        private val buildManagerFactory: BuildManagerFactory,
        private val storeFactory: LMDBBuildStoreFactory,
        private val cacheFactory: Provider<BuildCache>)
    : IBuildManagerProvider {

    private val logger = LoggerFactory.getLogger(DummyCodeCompletionBuilder::class.java)

    private val buildManagers = ConcurrentHashMap<URI, BuildManager>()

    override fun getBuildManager(projectUri: URI): BuildManager {
        return this.buildManagers.getOrPut(projectUri) {
            val store = createStore(projectUri)
            val cache = this.cacheFactory.get()
            this.buildManagerFactory.create(store, cache)
        }
    }

    private fun createStore(projectUri: URI): BuildStore {
        val localStoreDir = getLocalStoreDir(projectUri)

        val store: BuildStore
        if (localStoreDir != null) {
            store = this.storeFactory.create(localStoreDir)
            logger.debug("Created PIE LMDB store at $localStoreDir for project $projectUri.")
        } else {
            store = InMemoryBuildStore()
            logger.warn("Could not create PIE LMDB store for project $projectUri because it is not on the local filesystem. Using an in-memory store instead.")
        }
        return store
    }

    private fun getLocalStoreDir(projectUri: URI): File? {
        return try {
            val projectRootDir = this.pathSrv.resolve(projectUri)
            val storeDir = projectRootDir.resolve(".pie")
            this.pathSrv.localPath(storeDir)
        } catch (_: RuntimeException) {
            // `projectUri` is not a local file system URI.
            null
        }
    }
}
