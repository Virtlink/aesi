package com.virtlink.paplj.codecompletion

import com.virtlink.editorservices.*
import com.virtlink.editorservices.codecompletion.*
import com.virtlink.logging.logger
import java.net.URI

class DummyCodeCompleter : ICodeCompletionService {

    @Suppress("PrivatePropertyName")
    private val LOG by logger()

    override fun getCompletionInfo(document: URI, caretOffset: Offset, cancellationToken: ICancellationToken): ICompletionInfo {
        LOG.info("$document: Completing at $caretOffset.")

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