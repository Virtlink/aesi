package com.virtlink.editorservices

import com.virtlink.logging.logger
import java.util.concurrent.locks.ReentrantReadWriteLock
import kotlin.concurrent.read
import kotlin.concurrent.write

/**
 * Default session manager implementation.
 */
class SessionManager : ISessionManager {

    @Suppress("PrivatePropertyName")
    private val LOG by logger()

    /** Lock. */
    private val lock = ReentrantReadWriteLock()

    override var id: SessionId? = null
        private set

    override fun start() {
        this.lock.read {
            if (this.id != null) {
                throw IllegalStateException("Another session is currently active.")
            }

            this.lock.write {
                this.id = SessionId()
                LOG.info("Started session with ID $id")
            }
        }
    }

    override fun stop() {
        this.lock.read {
            if (this.id == null) {
                LOG.warn("No session to stop")
                return
            }

            this.lock.write {
                this.id = SessionId()
                LOG.info("Stopped session with ID $id")
            }
        }
    }
}