package com.virtlink.editorservices.lsp

import com.sun.security.ntlm.Server
import org.eclipse.lsp4j.*
import org.eclipse.lsp4j.jsonrpc.ResponseErrorException
import org.eclipse.lsp4j.jsonrpc.messages.Either
import org.eclipse.lsp4j.jsonrpc.messages.ResponseError
import org.eclipse.lsp4j.services.*
import org.slf4j.LoggerFactory
import java.util.concurrent.CompletableFuture

open class AbstractLanguageServer : LanguageServer, LanguageClientAware, TextDocumentService, WorkspaceService {

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

    override fun initialize(params: InitializeParams): CompletableFuture<InitializeResult> {
        assertConnected()
        logger.debug("Server initializing")
        this.state = ServerState.ServerInitializing
        // TODO
        val capabilities = ServerCapabilities()
        // TODO
        logger.info("Server initialized")
        logger.debug("Client initializing")
        this.state = ServerState.ClientInitializing

        return CompletableFuture.completedFuture(InitializeResult(capabilities))
    }

    override fun initialized(params: InitializedParams) {
        assertConnected()
        logger.info("Client initialized")
        // TODO
        this.state = ServerState.Ready
        logger.info("Server ready")
    }

    override fun shutdown(): CompletableFuture<Any> {
        assertReady()
        logger.debug("Shutting down")
        this.state = ServerState.Shutdown
        // TODO
        logger.info("Shut down")
        return CompletableFuture.completedFuture(true)
    }

    override fun exit() {
        logger.info("Exiting")
        // TODO
    }

    override fun resolveCompletionItem(unresolved: CompletionItem): CompletableFuture<CompletionItem>
            = CompletableFuture.completedFuture(null)

    override fun codeAction(params: CodeActionParams): CompletableFuture<MutableList<out Command>>
            = CompletableFuture.completedFuture(null)

    override fun hover(position: TextDocumentPositionParams): CompletableFuture<Hover>
            = CompletableFuture.completedFuture(null)

    override fun documentHighlight(position: TextDocumentPositionParams): CompletableFuture<MutableList<out DocumentHighlight>>
            = CompletableFuture.completedFuture(null)

    override fun onTypeFormatting(params: DocumentOnTypeFormattingParams): CompletableFuture<MutableList<out TextEdit>>
            = CompletableFuture.completedFuture(null)

    override fun definition(position: TextDocumentPositionParams): CompletableFuture<MutableList<out Location>>
            = CompletableFuture.completedFuture(null)

    override fun rangeFormatting(params: DocumentRangeFormattingParams): CompletableFuture<MutableList<out TextEdit>>
            = CompletableFuture.completedFuture(null)

    override fun codeLens(params: CodeLensParams): CompletableFuture<MutableList<out CodeLens>>
            = CompletableFuture.completedFuture(null)

    override fun rename(params: RenameParams): CompletableFuture<WorkspaceEdit>
            = CompletableFuture.completedFuture(null)

    override fun completion(position: TextDocumentPositionParams): CompletableFuture<Either<MutableList<CompletionItem>, CompletionList>>
            = CompletableFuture.completedFuture(null)

    override fun documentSymbol(params: DocumentSymbolParams): CompletableFuture<MutableList<out SymbolInformation>>
            = CompletableFuture.completedFuture(null)

    override fun didOpen(params: DidOpenTextDocumentParams) { }

    override fun didSave(params: DidSaveTextDocumentParams) { }

    override fun signatureHelp(position: TextDocumentPositionParams): CompletableFuture<SignatureHelp>
            = CompletableFuture.completedFuture(null)

    override fun didClose(params: DidCloseTextDocumentParams) { }

    override fun formatting(params: DocumentFormattingParams): CompletableFuture<MutableList<out TextEdit>>
            = CompletableFuture.completedFuture(null)

    override fun didChange(params: DidChangeTextDocumentParams) { }

    override fun references(params: ReferenceParams): CompletableFuture<MutableList<out Location>>
            = CompletableFuture.completedFuture(null)

    override fun resolveCodeLens(unresolved: CodeLens): CompletableFuture<CodeLens>
            = CompletableFuture.completedFuture(null)

    override fun didChangeWatchedFiles(params: DidChangeWatchedFilesParams) { }

    override fun didChangeConfiguration(params: DidChangeConfigurationParams) { }

    override fun symbol(params: WorkspaceSymbolParams): CompletableFuture<MutableList<out SymbolInformation>>
            = CompletableFuture.completedFuture(null)

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