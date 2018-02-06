package com.virtlink.paplj.codecompletion

import com.virtlink.editorservices.*
import com.virtlink.editorservices.codecompletion.*
import com.virtlink.logging.logger
import java.net.URI

class DummyCodeCompleter : ICodeCompletionService {

    override val triggerCharacters: List<Char>
        get() = emptyList()

    @Suppress("PrivatePropertyName")
    private val LOG by logger()

    override fun configure(configuration: ICodeCompletionConfiguration) {
        // Nothing to do.
    }

    override fun getCompletionInfo(document: URI, caretOffset: Offset, cancellationToken: ICancellationToken?): ICompletionInfo {
        LOG.info("$document: Completing at $caretOffset.")

        val proposals = listOf(
                CompletionProposal("Hello",
                        description = "Description string",
                        content = "hello world!",
                        scopes = "meta.field,meta.static"),
                CompletionProposal("Local variable",
                        content = "local var",
                        scopes = "meta.variable,meta.internal"),
                CompletionProposal("Method",
                        content = "method()",
                        scopes = "meta.method,meta.abstract,meta.deprecated,meta.package"),
                CompletionProposal("if (then else)")
        )

        // TODO: Determine prefix according to language rules.
        return CompletionInfo("", proposals)
    }
}