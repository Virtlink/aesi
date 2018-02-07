package com.virtlink.dummy

import com.google.inject.Inject
import com.virtlink.editorservices.ISessionManager
import com.virtlink.editorservices.Offset
import com.virtlink.editorservices.ScopeNames
import com.virtlink.editorservices.codecompletion.CompletionInfo
import com.virtlink.editorservices.codecompletion.CompletionProposal
import com.virtlink.editorservices.codecompletion.ICompletionInfo
import com.virtlink.editorservices.resources.IResourceManager
import com.virtlink.pie.DocumentReq
import com.virtlink.pie.codecompletion.PieCodeCompletionService
import mb.pie.runtime.core.BuildContext
import mb.pie.runtime.core.Builder
import org.slf4j.LoggerFactory
import java.net.URI

class DummyCodeCompletionBuilder @Inject constructor(
        private val sessionManager: ISessionManager,
        private val resourceManager: IResourceManager)
    : Builder<PieCodeCompletionService.Input, ICompletionInfo> {

    companion object {
        @JvmField val id: String = "getCompletionInfo"
    }

    override val id = Companion.id

    private val logger = LoggerFactory.getLogger(DummyCodeCompletionBuilder::class.java)

    override fun BuildContext.build(input: PieCodeCompletionService.Input): ICompletionInfo
        = this.getCompletionInfo(
            input.document,
            input.caretOffset)

    private fun BuildContext.getCompletionInfo(
            document: URI,
            caretOffset: Offset):
            ICompletionInfo {

        logger.info("$document: Completing at $caretOffset.")

        val content = resourceManager.getContent(document)
        val version = content?.lastModificationStamp ?: -1
        val session = sessionManager.currentSessionId!!
        require(DocumentReq(document, version, session))

        val proposals = listOf(
                CompletionProposal("Hello",
                        description = "Description string",
                        content = "hello world!",
                        scopes = ScopeNames("meta.field", "meta.static")),
                CompletionProposal("Local variable",
                        content = "local var",
                        scopes = ScopeNames("meta.variable", "meta.internal")),
                CompletionProposal("Method",
                        content = "method()",
                        caret = 7,
                        scopes = ScopeNames("meta.method", "meta.abstract", "meta.deprecated", "meta.package")),
                CompletionProposal("if (then else)")
        )

        // TODO: Determine prefix according to language rules.
        return CompletionInfo("", proposals)
    }
}