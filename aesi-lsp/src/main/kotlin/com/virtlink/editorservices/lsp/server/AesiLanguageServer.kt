package com.virtlink.editorservices.lsp.server

import com.google.inject.Inject
import com.virtlink.editorservices.IDocument
import com.virtlink.editorservices.IProject
import com.virtlink.editorservices.ISessionManager
import com.virtlink.editorservices.codecompletion.ICodeCompletionService
import com.virtlink.editorservices.codecompletion.ICompletionProposal
import com.virtlink.editorservices.content.TextChange
import com.virtlink.editorservices.lsp.ProjectManager
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
import com.virtlink.editorservices.content.VersionedContent
import com.virtlink.editorservices.lsp.content.DocumentContentManager
import com.virtlink.logging.logger

class AesiLanguageServer @Inject constructor(
        private val codeCompletionService: ICodeCompletionService,
        private val remoteContentSource: RemoteContentSource,
        private val projectManager: ProjectManager,
        private val documentContentManager: DocumentContentManager,
        private val sessionManager: ISessionManager
) : AbstractLanguageServer() {

    @Suppress("PrivatePropertyName")
    private val LOG by logger()

    override fun doInitialize(params: InitializeParams): CompletableFuture<InitializeResult>
    = CompletableFutures.computeAsync {
        this.sessionManager.start()
        this.projectManager.open(URI(params.rootUri))

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
        this.projectManager.close()
        this.sessionManager.stop()

        // We need to return some non-empty result for VS Code to send an exit() notification,
        // which in our case means non-null. So we'll just reply `true`.
        true
    }

    override fun opened(params: DidOpenTextDocumentParams) {
        val document = getDocument(params.textDocument.uri)
        this.documentContentManager.open(document, params.textDocument.text, params.textDocument.version)
    }

    override fun changed(params: DidChangeTextDocumentParams) {
        val document = getDocument(params.textDocument.uri)
        val content = this.documentContentManager.getLatestContent(document)
        val currentVersion = (content as VersionedContent).version
        val changes = params.contentChanges.map { TextChange(it.range.toSpan(content)!!, it.text) }
        this.remoteContentSource.update(document, currentVersion, changes, params.textDocument.version)
    }

    override fun saving(params: WillSaveTextDocumentParams) {
        val document = getDocument(params.textDocument.uri)
        LOG.info("$document: Saving because ${params.reason}")
    }

    override fun saved(params: DidSaveTextDocumentParams) {
        val document = getDocument(params.textDocument.uri)
        LOG.info("$document: Saved")
    }

    override fun closed(params: DidCloseTextDocumentParams) {
        val document = getDocument(params.textDocument.uri)
        this.documentContentManager.close(document)
    }

    override fun doCompletion(position: TextDocumentPositionParams)
            : CompletableFuture<Either<MutableList<CompletionItem>, CompletionList>>
    = CompletableFutures.computeAsync {
        val project = getProject()
        val document = getDocument(position.textDocument.uri)
        val content = this.documentContentManager.getLatestContent(document)
        val offset = position.position.toOffset(content)
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
        // TODO: Documentation
//        item.documentation = proposal.documentation
        // TODO: Kind
        return item
    }

    private fun getProject(): IProject
            = this.projectManager.getProject()

    private fun getDocument(uri: String): IDocument
            = this.projectManager.getDocuments().getDocument(URI(uri))

//    private fun getDocumentData(uri: URI): Pair<IDocument, IDocumentContent> {
//        val project = this.projectManager.getProject()
//        val document = this.projectManager.getDocuments(project).getDocument(uri)
//        val content = this.documentContentManager.getContent(document)
//        val version = (content as? VersionedContent)?.version
//        // TODO: Do something with the version
//        return Pair(document, content)
//    }
}