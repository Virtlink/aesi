package com.virtlink.editorservices.lsp

import org.eclipse.lsp4j.*
import org.eclipse.lsp4j.jsonrpc.ResponseErrorException
import org.eclipse.lsp4j.jsonrpc.messages.Either
import org.eclipse.lsp4j.jsonrpc.messages.ResponseError
import org.eclipse.lsp4j.services.*
import org.slf4j.LoggerFactory
import java.util.concurrent.CompletableFuture

abstract class AbstractLanguageServer : LanguageServer, LanguageClientAware, TextDocumentService, WorkspaceService {

    private var logger = LoggerFactory.getLogger(AbstractLanguageServer::class.java)

    private var state: ServerState = ServerState.NewBorn
    protected var client: LanguageClient? = null

    override fun getTextDocumentService(): TextDocumentService = this

    override fun getWorkspaceService(): WorkspaceService = this

    init {
        logger.info("Server created")
    }

    override fun connect(client: LanguageClient) {
        this.client = client
    }

    /////////////
    // General //
    /////////////

    final override fun initialize(params: InitializeParams)
            : CompletableFuture<InitializeResult> {
        assertConnected()
        logger.debug("Server initializing")
        this.state = ServerState.ServerInitializing
        return doInitialize(params)
                .thenApply { v ->
                    logger.info("Server initialized")
                    logger.debug("Client initializing")
                    this.state = ServerState.ClientInitializing
                    v
                }
    }

    open fun doInitialize(params: InitializeParams)
            : CompletableFuture<InitializeResult> {
        return CompletableFuture.completedFuture(InitializeResult(ServerCapabilities()))
    }


    final override fun initialized(params: InitializedParams) {
        assertConnected()
        logger.info("Client initialized")
        hasInitialized(params)
        this.state = ServerState.Ready
        logger.info("Server ready")
    }

    open fun hasInitialized(params: InitializedParams) { /* Nothing to do. */ }


    final override fun shutdown(): CompletableFuture<Any> {
        return CompletableFuture
                .runAsync {
                    assertReady()
                    logger.debug("Shutting down")
                    this.state = ServerState.Shutdown
                }
                .thenCompose { doShutdown() }
                .thenApply { v ->
                    logger.info("Shut down")
                    v
                }
    }

    open fun doShutdown()
            : CompletableFuture<Any>
    {
        // We need to return some non-empty result for VS Code to send an exit() notification,
        // which in our case means non-null. So we'll just reply `true`.
        return CompletableFuture.completedFuture(true)
    }


    final override fun exit() {
        logger.info("Exiting")
        doExit()
    }

    open fun doExit() {
        val exitCode = if (this.state == ServerState.Shutdown) 0 else 1

        System.exit(exitCode)
    }

    ///////////////
    // Workspace //
    ///////////////

    final override fun didChangeConfiguration(params: DidChangeConfigurationParams) {
        if (!assumeReady()) return
        configurationChanged(params)
    }

    open fun configurationChanged(params: DidChangeConfigurationParams) { /* Nothing to do. */ }


    final override fun didChangeWatchedFiles(params: DidChangeWatchedFilesParams) {
        if (!assumeReady()) return
        watchedFilesChanged(params)
    }

    open fun watchedFilesChanged(params: DidChangeWatchedFilesParams) { /* Nothing to do. */ }


    final override fun symbol(params: WorkspaceSymbolParams)
            : CompletableFuture<MutableList<out SymbolInformation>> {
        assertReady()
        return doSymbol(params)
    }

    open fun doSymbol(params: WorkspaceSymbolParams)
            : CompletableFuture<MutableList<out SymbolInformation>>
            = throw UnsupportedOperationException()


    final override fun executeCommand(params: ExecuteCommandParams?)
            : CompletableFuture<Any> {
        assertReady()
        return doExecuteCommand(params)
    }

    open fun doExecuteCommand(params: ExecuteCommandParams?)
            : CompletableFuture<Any>
            = throw UnsupportedOperationException()

    //////////////
    // Document //
    //////////////

    final override fun didOpen(params: DidOpenTextDocumentParams) {
        if (!assumeReady()) return
        opened(params)
    }

    open fun opened(params: DidOpenTextDocumentParams) { /* Nothing to do. */ }

    final override fun didChange(params: DidChangeTextDocumentParams) {
        if (!assumeReady()) return
        changed(params)
    }

    open fun changed(params: DidChangeTextDocumentParams) { /* Nothing to do. */ }

    final override fun willSave(params: WillSaveTextDocumentParams) {
        if (!assumeReady()) return
        saving(params)
    }

    open fun saving(params: WillSaveTextDocumentParams) { /* Nothing to do. */ }


    final override fun willSaveWaitUntil(params: WillSaveTextDocumentParams)
            : CompletableFuture<MutableList<TextEdit>> {
        assertReady()
        return doWillSaveWaitUntil(params)
    }

    open fun doWillSaveWaitUntil(params: WillSaveTextDocumentParams)
            : CompletableFuture<MutableList<TextEdit>>
            = throw UnsupportedOperationException()


    final override fun didSave(params: DidSaveTextDocumentParams) {
        if (!assumeReady()) return
        saved(params)
    }

    open fun saved(params: DidSaveTextDocumentParams) { /* Nothing to do. */ }


    final override fun didClose(params: DidCloseTextDocumentParams) {
        if (!assumeReady()) return
        closed(params)
    }

    open fun closed(params: DidCloseTextDocumentParams) { /* Nothing to do. */ }


    final override fun completion(position: TextDocumentPositionParams)
            : CompletableFuture<Either<MutableList<CompletionItem>, CompletionList>> {
        assertReady()
        return doCompletion(position)
    }

    open fun doCompletion(position: TextDocumentPositionParams)
            : CompletableFuture<Either<MutableList<CompletionItem>, CompletionList>>
            = throw UnsupportedOperationException()


    final override fun resolveCompletionItem(unresolved: CompletionItem)
            : CompletableFuture<CompletionItem> {
        assertReady()
        return doResolveCompletionItem(unresolved)
    }

    open fun doResolveCompletionItem(unresolved: CompletionItem)
            : CompletableFuture<CompletionItem>
            = throw UnsupportedOperationException()


    final override fun hover(position: TextDocumentPositionParams)
            : CompletableFuture<Hover> {
        assertReady()
        return doHover(position)
    }

    open fun doHover(position: TextDocumentPositionParams)
            : CompletableFuture<Hover>
            = throw UnsupportedOperationException()


    final override fun signatureHelp(position: TextDocumentPositionParams)
            : CompletableFuture<SignatureHelp> {
        assertReady()
        return doSignatureHelp(position)
    }

    open fun doSignatureHelp(position: TextDocumentPositionParams)
            : CompletableFuture<SignatureHelp>
            = throw UnsupportedOperationException()


    final override fun references(params: ReferenceParams)
            : CompletableFuture<MutableList<out Location>> {
        assertReady()
        return doReferences(params)
    }

    open fun doReferences(params: ReferenceParams)
            : CompletableFuture<MutableList<out Location>>
            = throw UnsupportedOperationException()


    final override fun documentHighlight(position: TextDocumentPositionParams)
            : CompletableFuture<MutableList<out DocumentHighlight>> {
        assertReady()
        return doDocumentHighlight(position)
    }

    open fun doDocumentHighlight(position: TextDocumentPositionParams)
            : CompletableFuture<MutableList<out DocumentHighlight>>
            = throw UnsupportedOperationException()


    final override fun documentSymbol(params: DocumentSymbolParams)
            : CompletableFuture<MutableList<out SymbolInformation>> {
        assertReady()
        return doDocumentSymbol(params)
    }

    open fun doDocumentSymbol(params: DocumentSymbolParams)
            : CompletableFuture<MutableList<out SymbolInformation>>
            = throw UnsupportedOperationException()


    final override fun formatting(params: DocumentFormattingParams)
            : CompletableFuture<MutableList<out TextEdit>> {
        assertReady()
        return doFormatting(params)
    }

    open fun doFormatting(params: DocumentFormattingParams)
            : CompletableFuture<MutableList<out TextEdit>>
            = throw UnsupportedOperationException()


    final override fun rangeFormatting(params: DocumentRangeFormattingParams)
            : CompletableFuture<MutableList<out TextEdit>> {
        assertReady()
        return doRangeFormatting(params)
    }

    open fun doRangeFormatting(params: DocumentRangeFormattingParams)
            : CompletableFuture<MutableList<out TextEdit>>
            = throw UnsupportedOperationException()


    final override fun onTypeFormatting(params: DocumentOnTypeFormattingParams)
            : CompletableFuture<MutableList<out TextEdit>> {
        assertReady()
        return doOnTypeFormatting(params)
    }

    open fun doOnTypeFormatting(params: DocumentOnTypeFormattingParams)
            : CompletableFuture<MutableList<out TextEdit>>
            = throw UnsupportedOperationException()


    final override fun definition(position: TextDocumentPositionParams)
            : CompletableFuture<MutableList<out Location>> {
        assertReady()
        return doDefinition(position)
    }

    open fun doDefinition(position: TextDocumentPositionParams)
            : CompletableFuture<MutableList<out Location>>
            = throw UnsupportedOperationException()


    final override fun codeAction(params: CodeActionParams)
            : CompletableFuture<MutableList<out Command>> {
        assertReady()
        return doCodeAction(params)
    }

    open fun doCodeAction(params: CodeActionParams)
            : CompletableFuture<MutableList<out Command>>
            = throw UnsupportedOperationException()


    final override fun codeLens(params: CodeLensParams)
            : CompletableFuture<MutableList<out CodeLens>> {
        assertReady()
        return doCodeLens(params)
    }

    open fun doCodeLens(params: CodeLensParams)
            : CompletableFuture<MutableList<out CodeLens>>
            = throw UnsupportedOperationException()


    final override fun resolveCodeLens(unresolved: CodeLens)
            : CompletableFuture<CodeLens> {
        assertReady()
        return doResolveCodeLens(unresolved)
    }

    open fun doResolveCodeLens(unresolved: CodeLens)
            : CompletableFuture<CodeLens>
            = throw UnsupportedOperationException()


    final override fun documentLink(params: DocumentLinkParams?)
            : CompletableFuture<MutableList<DocumentLink>> {
        assertReady()
        return doDocumentLink(params)
    }

    open fun doDocumentLink(params: DocumentLinkParams?)
            : CompletableFuture<MutableList<DocumentLink>>
            = throw UnsupportedOperationException()


    final override fun documentLinkResolve(params: DocumentLink?)
            : CompletableFuture<DocumentLink> {
        assertReady()
        return doDocumentLinkResolve(params)
    }

    open fun doDocumentLinkResolve(params: DocumentLink?)
            : CompletableFuture<DocumentLink>
            = throw UnsupportedOperationException()


    final override fun rename(params: RenameParams)
            : CompletableFuture<WorkspaceEdit> {
        assertReady()
        return doRename(params)
    }

    open fun doRename(params: RenameParams)
            : CompletableFuture<WorkspaceEdit>
            = throw UnsupportedOperationException()

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private fun assumeReady(): Boolean {
        val msg = when (this.state) {
            ServerState.Ready -> return true
            ServerState.NewBorn -> "Unexpected notification, server not initialized."
            ServerState.ServerInitializing -> "Unexpected notification, server initializing."
            ServerState.ClientInitializing -> "Unexpected notification, client initializing."
            ServerState.Shutdown -> "Unexpected notification, server shut down."
        }
        logger.warn(msg)
        return false
    }

    /**
     * Asserts that the server has been initialized,
     * otherwise throws an exception that returns an error.
     */
    private fun assertReady() {
        val msg = when (this.state) {
            ServerState.Ready -> return
            ServerState.NewBorn -> "Unexpected request, server not initialized."
            ServerState.ServerInitializing -> "Unexpected request, server initializing."
            ServerState.ClientInitializing -> "Unexpected request, client initializing."
            ServerState.Shutdown -> "Unexpected request, server shut down."
        }
        throw ResponseErrorException(ResponseError(-32001, msg, null))
    }

    /**
     * Asserts that the server has been connected to a client.
     */
    private fun assertConnected() {
        if (this.client == null)
            throw IllegalStateException("Server is not connected to a client.")
    }

    enum class ServerState {
        NewBorn,
        ServerInitializing,
        ClientInitializing,
        Ready,
        Shutdown
    }
}