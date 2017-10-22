package com.virtlink.paplj.codecompletion

import com.virtlink.editorservices.*
import com.virtlink.editorservices.codecompletion.*
import org.slf4j.LoggerFactory
import java.util.*

class DummyCodeCompleter : ICodeCompletionService {

    private var logger = LoggerFactory.getLogger(DummyCodeCompleter::class.java)

    override fun getCompletionInfo(project: IProject, document: IDocument, caretOffset: Offset, cancellationToken: ICancellationToken?): ICompletionInfo {
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