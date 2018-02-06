package com.virtlink.pie

import com.google.inject.Inject
import com.virtlink.editorservices.ISessionManager
import com.virtlink.editorservices.SessionId
import com.virtlink.editorservices.resources.IResourceManager
import mb.pie.runtime.core.*
import mb.pie.runtime.core.impl.Build
import mb.pie.runtime.core.impl.Req
import java.net.URI


/**
 * A document requirement.
 */
data class DocumentReq(val document: URI, val stamp: Long, val session: SessionId) : Req {

    companion object {
        @Inject lateinit private var sessionManager: ISessionManager
        @Inject lateinit private var resourceManager: IResourceManager
    }

    override fun <I : In, O : Out> makeConsistent(
            requiringApp: BuildApp<I, O>,
            requiringResult: BuildRes<I, O>,
            build: Build,
            logger: BuildLogger)
            : BuildReason? {
//        logger.checkReqStart(requiringApp, this)

        val newSession = sessionManager.id!!
        val content = resourceManager.getContent(document)
        val newStamp = content?.lastModificationStamp ?: -1
        val reason = if (newSession != session || newStamp != stamp) {
            InconsistentBuildReq(requiringResult, this, newStamp, newSession)
        } else {
            null
        }

//        logger.checkReqEnd(requiringApp, this, reason)

        return reason
    }

    data class InconsistentBuildReq(
            val requiringResult: UBuildRes,
            val req: DocumentReq,
            val newVersion: Long,
            val newSession: SessionId) : BuildReason {
        override fun toString() = "required document ${req.document} is inconsistent"
    }
}