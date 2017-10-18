package com.virtlink.dummy

import com.virtlink.editorservices.ICancellationToken
import com.virtlink.editorservices.IDocument
import com.virtlink.editorservices.IProject
import com.virtlink.editorservices.codecompletion.CompletionInfo
import com.virtlink.editorservices.codecompletion.CompletionProposal
import com.virtlink.editorservices.codecompletion.ICompletionInfo
import mb.pie.runtime.core.BuildContext
import mb.pie.runtime.core.Builder
import org.slf4j.LoggerFactory

class DummyCodeCompletionBuilder
    : Builder<DummyCodeCompletionBuilder.Input, ICompletionInfo> {

    companion object {
        @JvmField val id: String = "getCompletionInfo"
    }

    override val id = Companion.id

    private val logger = LoggerFactory.getLogger(DummyCodeCompletionBuilder::class.java)

    override fun BuildContext.build(input: Input): ICompletionInfo
        = this.getCompletionInfo(
            input.project,
            input.document,
            input.caretOffset,
            input.cancellationToken)

    @Suppress("UNUSED_PARAMETER", "unused")
    private fun BuildContext.getCompletionInfo(
            project: IProject,
            document: IDocument,
            caretOffset: Int,
            cancellationToken: ICancellationToken?):
            ICompletionInfo {

        logger.info("$document: Completing at $caretOffset.")

        val proposals = listOf(
                CompletionProposal("Hello",
                        project = project,
                        document = document,
                        description = "Description string",
                        documentation = "Extensive documentation",
                        insertionText = "hello world!",
                        type = "MyType",
                        kind = "meta.field",
                        attributes = listOf("meta.static")),
                CompletionProposal("Local variable",
                        project = project,
                        document = document,
                        insertionText = "local var",
                        type = "String",
                        kind = "meta.variable",
                        attributes = listOf("meta.internal")),
                CompletionProposal("Method",
                        project = project,
                        document = document,
                        insertionText = "method()",
                        caret = 7,
                        type = "String",
                        kind = "meta.method",
                        attributes = listOf("meta.abstract", "meta.deprecated", "meta.package")),
                CompletionProposal("if (then else)",
                        project = project,
                        document = document)
        )

        // TODO: Determine prefix according to language rules.
        return CompletionInfo("", proposals)
    }
}