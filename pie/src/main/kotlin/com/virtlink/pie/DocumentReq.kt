package com.virtlink.pie

import com.google.inject.Inject
import com.virtlink.editorservices.IDocument
import com.virtlink.editorservices.ISessionManager
import com.virtlink.editorservices.SessionId
import com.virtlink.editorservices.content.IContentManager
import com.virtlink.editorservices.content.VersionedContent
import mb.pie.runtime.core.*
import mb.pie.runtime.core.impl.Build
import mb.pie.runtime.core.impl.Req


/**
 * A document requirement.
 */
data class DocumentReq(val document: IDocument, val version: Int, val session: SessionId) : Req {

    companion object {
        @Inject lateinit private var sessionManager: ISessionManager
        @Inject lateinit private var contentManager: IContentManager
    }

    override fun <I : In, O : Out> makeConsistent(
            requiringApp: BuildApp<I, O>,
            requiringResult: BuildRes<I, O>,
            build: Build,
            logger: BuildLogger)
            : BuildReason? {
//        logger.checkReqStart(requiringApp, this)

        val newSession = sessionManager.id!!
        val content = contentManager.getLatestContent(document)
        val newVersion = (content as? VersionedContent)?.version ?: -1
        val reason = if (newSession != session || newVersion != version) {
            InconsistentBuildReq(requiringResult, this, newVersion, newSession)
        } else {
            null
        }

//        logger.checkReqEnd(requiringApp, this, reason)

        return reason
    }

    data class InconsistentBuildReq(
            val requiringResult: UBuildRes,
            val req: DocumentReq,
            val newVersion: Int,
            val newSession: SessionId) : BuildReason {
        override fun toString() = "required document ${req.document} is inconsistent"
    }
}