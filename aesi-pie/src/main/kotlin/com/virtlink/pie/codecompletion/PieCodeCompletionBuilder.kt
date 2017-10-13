package com.virtlink.pie.codecompletion

import com.virtlink.editorservices.ICancellationToken
import com.virtlink.editorservices.IDocument
import com.virtlink.editorservices.IProject
import com.virtlink.editorservices.codecompletion.CompletionInfo2
import com.virtlink.editorservices.codecompletion.CompletionProposal2
import com.virtlink.editorservices.codecompletion.ICompletionInfo2
import mb.pie.runtime.core.BuildContext
import mb.pie.runtime.core.Builder
import mb.vfs.path.PPathImpl
import org.slf4j.LoggerFactory
import java.io.Serializable

class PieCodeCompletionBuilder
    : Builder<PieCodeCompletionBuilder.Input, ICompletionInfo2> {

    companion object {
        @JvmField val id: String = "getCompletionInfo"
    }

    override val id = Companion.id

    data class Input(val project: IProject,
                     val document: IDocument,
                     val caretOffset: Int)
        : Serializable

    private val logger = LoggerFactory.getLogger(PieCodeCompletionBuilder::class.java)

    override fun BuildContext.build(input: Input): ICompletionInfo2
        = this.getCompletionInfo(
            input.project,
            input.document,
            input.caretOffset,
            null)

    @Suppress("UNUSED_PARAMETER", "unused")
    private fun BuildContext.getCompletionInfo(
            project: IProject,
            document: IDocument,
            caretOffset: Int,
            cancellationToken: ICancellationToken?):
            ICompletionInfo2 {

        require(PPathImpl(document.uri))

        logger.info("$document: Completing at $caretOffset.")

        val proposals = listOf(
                CompletionProposal2("Hello",
                        project = project,
                        document = document,
                        description = "Description string",
                        documentation = "Extensive documentation",
                        insertionText = "hello world!",
                        type = "MyType",
                        kind = "meta.field",
                        attributes = listOf("meta.static")),
                CompletionProposal2("Local variable",
                        project = project,
                        document = document,
                        insertionText = "local var",
                        type = "String",
                        kind = "meta.variable",
                        attributes = listOf("meta.internal")),
                CompletionProposal2("Method",
                        project = project,
                        document = document,
                        insertionText = "method()",
                        caret = 7,
                        type = "String",
                        kind = "meta.method",
                        attributes = listOf("meta.abstract", "meta.deprecated", "meta.package")),
                CompletionProposal2("if (then else)",
                        project = project,
                        document = document)
        )

        // TODO: Determine prefix according to language rules.
        return CompletionInfo2("", proposals)
    }
}