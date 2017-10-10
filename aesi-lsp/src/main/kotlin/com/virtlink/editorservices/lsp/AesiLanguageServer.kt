package com.virtlink.editorservices.lsp

import com.google.inject.Inject
import com.virtlink.editorservices.codecompletion.ICodeCompleter
import com.virtlink.editorservices.codecompletion.ICompletionProposal
import org.eclipse.lsp4j.*
import org.eclipse.lsp4j.jsonrpc.CompletableFutures
import org.eclipse.lsp4j.jsonrpc.ResponseErrorException
import org.eclipse.lsp4j.jsonrpc.messages.Either
import org.eclipse.lsp4j.jsonrpc.messages.ResponseError
import org.eclipse.lsp4j.jsonrpc.messages.ResponseErrorCode
import java.util.concurrent.CompletableFuture

class AesiLanguageServer @Inject constructor(
        private val codeCompletionService: ICodeCompleter,
        private val documentManager: DocumentManager,
        private val projectManager: ProjectManager
) : AbstractLanguageServer() {

    override fun doInitialize(params: InitializeParams): CompletableFuture<InitializeResult>
    = CompletableFutures.computeAsync {
        val capabilities = ServerCapabilities()
        capabilities.completionProvider = CompletionOptions(false, listOf(".", " "))
        InitializeResult(capabilities)
    }

    override fun doCompletion(position: TextDocumentPositionParams)
            : CompletableFuture<Either<MutableList<CompletionItem>, CompletionList>>
    = CompletableFutures.computeAsync {
        val project = this.projectManager.getProject()
        val document = this.documentManager.getDocument(position.textDocument)
        val offset = position.position.toOffset(document)
                ?: throw ResponseErrorException(ResponseError(ResponseErrorCode.InvalidParams,
                "Position not found within document.", position.position))
        val info = this.codeCompletionService.complete(project, document, offset, it.toCancellationToken())
        val proposals = info.proposals.map { toCompletionItem(it) }
        val list = CompletionList(proposals)
        Either.forRight<MutableList<CompletionItem>, CompletionList>(list)
    }

    private fun toCompletionItem(proposal: ICompletionProposal): CompletionItem {
        val item = CompletionItem(proposal.label)
        item.insertText = proposal.insertionText
        item.detail = proposal.description
        item.documentation = proposal.documentation
        // TODO: Kind
        return item
    }
}