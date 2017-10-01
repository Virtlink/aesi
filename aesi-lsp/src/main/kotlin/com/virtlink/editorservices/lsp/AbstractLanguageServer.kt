package com.virtlink.editorservices.lsp

import org.eclipse.lsp4j.*
import org.eclipse.lsp4j.jsonrpc.ResponseErrorException
import org.eclipse.lsp4j.jsonrpc.messages.Either
import org.eclipse.lsp4j.jsonrpc.messages.ResponseError
import org.eclipse.lsp4j.services.*
import org.slf4j.LoggerFactory
import java.util.concurrent.CompletableFuture

class AbstractLanguageServer : LanguageServer, LanguageClientAware {//}, TextDocumentService, WorkspaceService {

    var logger = LoggerFactory.getLogger(AbstractLanguageServer::class.java)

    var state: ServerState = ServerState.NewBorn
    var client: LanguageClient? = null

    override fun getTextDocumentService(): TextDocumentService = TODO()//this

    override fun getWorkspaceService(): WorkspaceService = TODO()//this

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
        logger.info("Server initialized")
        logger.debug("Client initializing")
        this.state = ServerState.ClientInitializing
        return CompletableFuture.completedFuture(null)
    }

    override fun initialized(params: InitializedParams) {
        assertConnected()
        assertReady()
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
        return CompletableFuture.completedFuture(null)
    }

    override fun exit() {
        logger.info("Exiting")
        // TODO
    }

/*

    override fun resolveCompletionItem(unresolved: CompletionItem?): CompletableFuture<CompletionItem> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun codeAction(params: CodeActionParams?): CompletableFuture<MutableList<out Command>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hover(position: TextDocumentPositionParams?): CompletableFuture<Hover> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun documentHighlight(position: TextDocumentPositionParams?): CompletableFuture<MutableList<out DocumentHighlight>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onTypeFormatting(params: DocumentOnTypeFormattingParams?): CompletableFuture<MutableList<out TextEdit>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun definition(position: TextDocumentPositionParams?): CompletableFuture<MutableList<out Location>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun rangeFormatting(params: DocumentRangeFormattingParams?): CompletableFuture<MutableList<out TextEdit>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun codeLens(params: CodeLensParams?): CompletableFuture<MutableList<out CodeLens>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun rename(params: RenameParams?): CompletableFuture<WorkspaceEdit> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun completion(position: TextDocumentPositionParams?): CompletableFuture<Either<MutableList<CompletionItem>, CompletionList>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun documentSymbol(params: DocumentSymbolParams?): CompletableFuture<MutableList<out SymbolInformation>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun didOpen(params: DidOpenTextDocumentParams?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun didSave(params: DidSaveTextDocumentParams?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun signatureHelp(position: TextDocumentPositionParams?): CompletableFuture<SignatureHelp> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun didClose(params: DidCloseTextDocumentParams?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun formatting(params: DocumentFormattingParams?): CompletableFuture<MutableList<out TextEdit>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun didChange(params: DidChangeTextDocumentParams?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun references(params: ReferenceParams?): CompletableFuture<MutableList<out Location>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun resolveCodeLens(unresolved: CodeLens?): CompletableFuture<CodeLens> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun didChangeWatchedFiles(params: DidChangeWatchedFilesParams?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun didChangeConfiguration(params: DidChangeConfigurationParams?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun symbol(params: WorkspaceSymbolParams?): CompletableFuture<MutableList<out SymbolInformation>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
*/
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