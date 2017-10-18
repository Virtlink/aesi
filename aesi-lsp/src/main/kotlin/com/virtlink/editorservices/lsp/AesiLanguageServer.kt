package com.virtlink.editorservices.lsp

import com.google.inject.Inject
import com.virtlink.editorservices.Offset
import com.virtlink.editorservices.Span
import com.virtlink.editorservices.codecompletion.ICodeCompletionService
import com.virtlink.editorservices.codecompletion.ICompletionProposal
import com.virtlink.editorservices.documents.IDocumentContentManager
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
        private val codeCompletionService: ICodeCompletionService,
        private val projectManager: ProjectManager,
        private val documentContentManager: IDocumentContentManager
) : AbstractLanguageServer() {

    private val logger = LoggerFactory.getLogger(AesiLanguageServer::class.java)

    override fun doInitialize(params: InitializeParams): CompletableFuture<InitializeResult>
    = CompletableFutures.computeAsync {
        this.projectManager.openProject(URI(params.rootUri))

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
        val project = this.projectManager.getProject()
        val document = project.documents.getDocument(URI(params.textDocument.uri))
        val content = this.documentContentManager.openDocument(document)
        content.update(Span(Offset(0), Offset(document.length)), params.textDocument.text)
    }

    override fun changed(params: DidChangeTextDocumentParams) {
        val project = this.projectManager.getProject()
        val document = project.documents.getDocument(URI(params.textDocument.uri))
        if (document !is VirtualDocument) {
            logger.error("${document.relativeUri}: Document was not opened, changes ignored.")
            return
        }
        params.contentChanges.forEach {
            val offset = document.getOffset(it.range.start.line, it.range.start.character)!!
            val length = it.rangeLength
            val newText = it.text
            document.update(offset, length, newText)
        }
        logger.debug("${document.relativeUri}: Applied ${params.contentChanges.size} changes.")
        logger.trace("${document.relativeUri}:\n${document.text}")
    }

    override fun saving(params: WillSaveTextDocumentParams) {
        val project = this.projectManager.getProject()
        val document = project.documents.getDocument(URI(params.textDocument.uri))
        logger.info("${document.relativeUri}: Saving because ${params.reason}.")
    }

    override fun saved(params: DidSaveTextDocumentParams) {
        val project = this.projectManager.getProject()
        val document = project.documents.getDocument(URI(params.textDocument.uri))
        logger.info("${document.relativeUri}: Saved.")
    }

    override fun closed(params: DidCloseTextDocumentParams) {
        val project = this.projectManager.getProject()
        project.documents.closeDocument(URI(params.textDocument.uri))
    }

    override fun doCompletion(position: TextDocumentPositionParams)
            : CompletableFuture<Either<MutableList<CompletionItem>, CompletionList>>
    = CompletableFutures.computeAsync {
        val project = this.projectManager.getProject()
        val document = project.documents.getDocument(URI(position.textDocument.uri))
        val offset = document.getOffset(position.position.line, position.position.character)
                ?: throw ResponseErrorException(ResponseError(ResponseErrorCode.InvalidParams,
                "Position not found within document.", position.position))
        val info = this.codeCompletionService.getCompletionInfo(project, document, offset, it.toCancellationToken())
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