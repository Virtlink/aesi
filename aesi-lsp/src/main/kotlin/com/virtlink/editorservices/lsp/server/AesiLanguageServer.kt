package com.virtlink.editorservices.lsp.server

import com.google.inject.Inject
import com.virtlink.editorservices.codecompletion.ICodeCompletionService
import com.virtlink.editorservices.codecompletion.ICompletionProposal
import com.virtlink.editorservices.documents.content.DocumentChange
import com.virtlink.editorservices.lsp.ProjectManager
import com.virtlink.editorservices.lsp.documents.LspDocumentContentManager
import com.virtlink.editorservices.lsp.toCancellationToken
import com.virtlink.editorservices.lsp.toOffset
import com.virtlink.editorservices.lsp.toSpan
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
        private val documentContentManager: LspDocumentContentManager
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
        this.documentContentManager.openDocument(document, params.textDocument.text, params.textDocument.version)
    }

    override fun changed(params: DidChangeTextDocumentParams) {
        val project = this.projectManager.getProject()
        val document = project.documents.getDocument(URI(params.textDocument.uri))
        val content = this.documentContentManager.getContent(document)
        val changes = params.contentChanges.map { DocumentChange(it.range.toSpan(content)!!, it.text) }
        this.documentContentManager.updateDocument(document, changes, params.textDocument.version)
    }

    override fun saving(params: WillSaveTextDocumentParams) {
        val project = this.projectManager.getProject()
        val document = project.documents.getDocument(URI(params.textDocument.uri))
        logger.info("${document}: Saving because ${params.reason}")
    }

    override fun saved(params: DidSaveTextDocumentParams) {
        val project = this.projectManager.getProject()
        val document = project.documents.getDocument(URI(params.textDocument.uri))
        logger.info("${document}: Saved")
    }

    override fun closed(params: DidCloseTextDocumentParams) {
        val project = this.projectManager.getProject()
        val document = project.documents.getDocument(URI(params.textDocument.uri))
        this.documentContentManager.closeDocument(document)
    }

    override fun doCompletion(position: TextDocumentPositionParams)
            : CompletableFuture<Either<MutableList<CompletionItem>, CompletionList>>
    = CompletableFutures.computeAsync {
        val project = this.projectManager.getProject()
        val document = project.documents.getDocument(URI(position.textDocument.uri))
        val content = this.documentContentManager.getContent(document)
        val offset = position.position.toOffset(content)
                ?: throw ResponseErrorException(ResponseError(ResponseErrorCode.InvalidParams,
                "Position not found within document.", position.position))
        val info = this.codeCompletionService.getCompletionInfo(document, offset, it.toCancellationToken())
        val proposals = info.proposals.map { toCompletionItem(it) }
        val list = CompletionList(proposals)
        Either.forRight<MutableList<CompletionItem>, CompletionList>(list)
    }

    private fun toCompletionItem(proposal: ICompletionProposal): CompletionItem {
        val item = CompletionItem(proposal.label)
        item.insertText = proposal.insertionText
        item.detail = proposal.description
        // TODO: Documentation
//        item.documentation = proposal.documentation
        // TODO: Kind
        return item
    }
}