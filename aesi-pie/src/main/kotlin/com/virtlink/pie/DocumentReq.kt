package com.virtlink.pie

import com.virtlink.editorservices.IDocument
import com.virtlink.editorservices.SessionId
import mb.pie.runtime.core.*
import mb.pie.runtime.core.impl.Build
import mb.pie.runtime.core.impl.Req

/**
 * A document requirement.
 */
data class DocumentReq(val document: IDocument, val version: Int, val session: SessionId) : Req {

    override fun <I : In, O : Out> makeConsistent(
            requiringApp: BuildApp<I, O>,
            requiringResult: BuildRes<I, O>,
            build: Build,
            logger: BuildLogger)
            : BuildReason? {
//        logger.checkReqStart(requiringApp, this)
        // TODO: Get session ID and compare
        val newSession = SessionId()
        // TODO: Get latest version of document and compare.
        val newVersion = -1
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