package com.virtlink.dummy

import com.virtlink.editorservices.ICancellationToken
import com.virtlink.editorservices.IDocument
import com.virtlink.editorservices.Offset
import com.virtlink.editorservices.codecompletion.CompletionInfo
import com.virtlink.editorservices.codecompletion.CompletionProposal
import com.virtlink.editorservices.codecompletion.ICompletionInfo
import com.virtlink.pie.codecompletion.PieCodeCompletionService
import mb.pie.runtime.core.BuildContext
import mb.pie.runtime.core.Builder
import org.slf4j.LoggerFactory

class DummyCodeCompletionBuilder
    : Builder<PieCodeCompletionService.Input, ICompletionInfo> {

    companion object {
        @JvmField val id: String = "getCompletionInfo"
    }

    override val id = Companion.id

    private val logger = LoggerFactory.getLogger(DummyCodeCompletionBuilder::class.java)

    override fun BuildContext.build(input: PieCodeCompletionService.Input): ICompletionInfo
        = this.getCompletionInfo(
            input.document,
            input.caretOffset,
            input.cancellationToken)

    @Suppress("UNUSED_PARAMETER", "unused")
    private fun BuildContext.getCompletionInfo(
            document: IDocument,
            caretOffset: Offset,
            cancellationToken: ICancellationToken?):
            ICompletionInfo {

        logger.info("$document: Completing at $caretOffset.")

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