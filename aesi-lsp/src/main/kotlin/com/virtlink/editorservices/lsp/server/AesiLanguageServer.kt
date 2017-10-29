package com.virtlink.editorservices.lsp.server

import com.google.inject.Inject
import com.virtlink.editorservices.ISessionManager
import com.virtlink.editorservices.codecompletion.ICodeCompletionService
import com.virtlink.editorservices.codecompletion.ICompletionProposal
import com.virtlink.editorservices.lsp.toCancellationToken
import com.virtlink.editorservices.lsp.toOffset
import com.virtlink.editorservices.lsp.toSpan
import org.eclipse.lsp4j.*
import org.eclipse.lsp4j.jsonrpc.CompletableFutures
import org.eclipse.lsp4j.jsonrpc.ResponseErrorException
import org.eclipse.lsp4j.jsonrpc.messages.Either
import org.eclipse.lsp4j.jsonrpc.messages.ResponseError
import org.eclipse.lsp4j.jsonrpc.messages.ResponseErrorCode
import java.net.URI
import java.util.concurrent.CompletableFuture
import com.virtlink.editorservices.lsp.content.RemoteContentSource
import com.virtlink.editorservices.lsp.content.DocumentContentManager
import com.virtlink.editorservices.lsp.resources.LspResourceManager
import com.virtlink.editorservices.resources.TextChange
import com.virtlink.logging.logger

class AesiLanguageServer @Inject constructor(
        private val codeCompletionService: ICodeCompletionService,
        private val remoteContentSource: RemoteContentSource,
        private val documentContentManager: DocumentContentManager,
        private val resourceManager: LspResourceManager,
        private val sessionManager: ISessionManager
) : AbstractLanguageServer() {

    @Suppress("PrivatePropertyName")
    private val LOG by logger()

    override fun doInitialize(params: InitializeParams): CompletableFuture<InitializeResult>
    = CompletableFutures.computeAsync {
        this.sessionManager.start()
        this.resourceManager.openProject(URI(params.rootUri))

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

    override fun doShutdown(): CompletableFuture<Any>
    = CompletableFutures.computeAsync {
        this.resourceManager.closeProject()
        this.sessionManager.stop()

        // We need to return some non-empty result for VS Code to send an exit() notification,
        // which in our case means non-null. So we'll just reply `true`.
        true
    }

    override fun opened(params: DidOpenTextDocumentParams) {
        val documentUri = this.resourceManager.getUriFromLSPUri(params.textDocument.uri)
        this.documentContentManager.open(documentUri, params.textDocument.text, params.textDocument.version.toLong())
    }

    override fun changed(params: DidChangeTextDocumentParams) {
        val documentUri = this.resourceManager.getUriFromLSPUri(params.textDocument.uri)
        val content = this.resourceManager.getContent(documentUri)
        if (content == null) {
            LOG.warn("$documentUri: Changes to unknown document.")
            return
        }
        val currentVersion = content.stamp
        val changes = params.contentChanges.map { TextChange(it.range.toSpan(content)!!, it.text) }
        this.remoteContentSource.update(documentUri, currentVersion, changes, params.textDocument.version.toLong())
    }

    override fun saving(params: WillSaveTextDocumentParams) {
        val documentUri = this.resourceManager.getUriFromLSPUri(params.textDocument.uri)
        LOG.info("$documentUri: Saving because ${params.reason}")
    }

    override fun saved(params: DidSaveTextDocumentParams) {
        val documentUri = this.resourceManager.getUriFromLSPUri(params.textDocument.uri)
        LOG.info("$documentUri: Saved")
    }

    override fun closed(params: DidCloseTextDocumentParams) {
        val documentUri = this.resourceManager.getUriFromLSPUri(params.textDocument.uri)
        this.documentContentManager.close(documentUri)
    }

    override fun doCompletion(position: TextDocumentPositionParams)
            : CompletableFuture<Either<MutableList<CompletionItem>, CompletionList>>
    = CompletableFutures.computeAsync {
        val documentUri = this.resourceManager.getUriFromLSPUri(position.textDocument.uri)
        val content = this.resourceManager.getContent(documentUri)
        val list: CompletionList
        if (content != null) {
            val offset = position.position.toOffset(content)
                    ?: throw ResponseErrorException(ResponseError(ResponseErrorCode.InvalidParams,
                    "Position not found within document.", position.position))
            val info = this.codeCompletionService.getCompletionInfo(documentUri, offset, it.toCancellationToken())
            if (info != null) {
                val proposals = info.proposals.map { toCompletionItem(it) }
                list = CompletionList(proposals)
            } else {
                list = CompletionList(emptyList())
            }
        } else {
            LOG.warn("$documentUri: Changes to unknown document.")
            list = CompletionList(emptyList())
        }
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