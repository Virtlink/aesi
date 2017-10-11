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
import org.slf4j.LoggerFactory
import java.net.URI
import java.util.concurrent.CompletableFuture

class AesiLanguageServer @Inject constructor(
        private val codeCompletionService: ICodeCompleter,
        private val documentManager: DocumentManager,
        private val projectManager: ProjectManager
) : AbstractLanguageServer() {

    private var logger = LoggerFactory.getLogger(AesiLanguageServer::class.java)

    override fun doInitialize(params: InitializeParams): CompletableFuture<InitializeResult>
    = CompletableFutures.computeAsync {
        val capabilities = ServerCapabilities()
        capabilities.completionProvider = CompletionOptions(false, listOf(".", " "))
        val documentSync = TextDocumentSyncOptions()
        documentSync.change = TextDocumentSyncKind.Incremental
        documentSync.openClose = true
        documentSync.save = SaveOptions(true)
        documentSync.willSave = true
//        documentSync.willSaveWaitUntil = true
        capabilities.textDocumentSync = Either.forRight<TextDocumentSyncKind, TextDocumentSyncOptions>(documentSync)
        InitializeResult(capabilities)
    }

    override fun opened(params: DidOpenTextDocumentParams) {
        this.documentManager.openDocument(URI(params.textDocument.uri), params.textDocument.text)
    }

    override fun changed(params: DidChangeTextDocumentParams) {
        logger.info("Changed ${params.textDocument}")
        val documentUri = URI(params.textDocument.uri)
        val document = this.documentManager.getDocument(documentUri)
        if (document !is VirtualDocument) {
            logger.error("Document was not opened, changes ignored to: $documentUri")
            return
        }
        params.contentChanges.forEach {
            val offset = document.getOffset(it.range.start.line, it.range.start.character)!!
            val length = it.rangeLength
            val newText = it.text
            document.update(offset, length, newText)
        }
        logger.debug("Applied ${params.contentChanges.size} changes to document ${document.uri}")
        logger.trace("Content:\n${document.text}")
    }

    override fun saving(params: WillSaveTextDocumentParams) {
        logger.info("Saving ${params.textDocument.uri} because ${params.reason}.")
    }

    override fun saved(params: DidSaveTextDocumentParams) {
        logger.info("Saved ${params.textDocument.uri}:\n${params.text}")
    }

    override fun closed(params: DidCloseTextDocumentParams) {
        this.documentManager.closeDocument(URI(params.textDocument.uri))
    }

    override fun doCompletion(position: TextDocumentPositionParams)
            : CompletableFuture<Either<MutableList<CompletionItem>, CompletionList>>
    = CompletableFutures.computeAsync {
        val project = this.projectManager.getProject()
        val document = this.documentManager.getDocument(URI(position.textDocument.uri))
        val offset = document.getOffset(position.position.line, position.position.character)
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