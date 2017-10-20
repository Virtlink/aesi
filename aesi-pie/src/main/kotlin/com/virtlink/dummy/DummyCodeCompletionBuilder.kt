package com.virtlink.dummy

import com.google.inject.Inject
import com.virtlink.editorservices.IDocument
import com.virtlink.editorservices.ISessionManager
import com.virtlink.editorservices.Offset
import com.virtlink.editorservices.codecompletion.CompletionInfo
import com.virtlink.editorservices.codecompletion.CompletionProposal
import com.virtlink.editorservices.codecompletion.ICompletionInfo
import com.virtlink.editorservices.content.IContentManager
import com.virtlink.editorservices.content.VersionedContent
import com.virtlink.pie.DocumentReq
import com.virtlink.pie.codecompletion.PieCodeCompletionService
import mb.pie.runtime.core.BuildContext
import mb.pie.runtime.core.Builder
import org.slf4j.LoggerFactory

class DummyCodeCompletionBuilder @Inject constructor(
        private val sessionManager: ISessionManager,
        private val contentManager: IContentManager)
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
            document: IDocument,
            caretOffset: Offset):
            ICompletionInfo {

        logger.info("$document: Completing at $caretOffset.")

        val content = contentManager.getLatestContent(document)
        val version = (content as? VersionedContent)?.version ?: -1
        val session = sessionManager.id!!
        require(DocumentReq(document, version, session))

        val proposals = listOf(
                CompletionProposal("Hello",
                        description = "Description string",
                        insertionText = "hello world!",
                        kind = "meta.field",
                        attributes = listOf("meta.static")),
                CompletionProposal("Local variable",
                        insertionText = "local var",
                        kind = "meta.variable",
                        attributes = listOf("meta.internal")),
                CompletionProposal("Method",
                        insertionText = "method()",
                        caret = 7,
                        kind = "meta.method",
                        attributes = listOf("meta.abstract", "meta.deprecated", "meta.package")),
                CompletionProposal("if (then else)")
        )

        // TODO: Determine prefix according to language rules.
        return CompletionInfo("", proposals)
    }
}