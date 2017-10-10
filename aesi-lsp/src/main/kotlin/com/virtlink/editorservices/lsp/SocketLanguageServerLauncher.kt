package com.virtlink.editorservices.lsp

import com.google.inject.Inject
import com.google.inject.assistedinject.Assisted
import org.eclipse.lsp4j.jsonrpc.Launcher
import org.eclipse.lsp4j.launch.LSPLauncher
import org.eclipse.lsp4j.services.LanguageClient
import org.eclipse.lsp4j.services.LanguageClientAware
import org.eclipse.lsp4j.services.LanguageServer
import org.slf4j.LoggerFactory
import java.io.PrintWriter
import java.net.InetSocketAddress
import java.net.Socket
import java.nio.channels.Channels
import java.nio.channels.ServerSocketChannel

class SocketLanguageServerLauncher @Inject constructor(
        @Assisted val port: Int,
        private val server: LanguageServer)
    : ILanguageServerLauncher {

    var logger = LoggerFactory.getLogger(SocketLanguageServerLauncher::class.java)

    override fun createLauncher(): Launcher<LanguageClient> {
        val serverSocket = ServerSocketChannel.open()
        val socketAddress = InetSocketAddress("localhost", this.port)
        serverSocket.bind(socketAddress)

        logger.info("Listening to $socketAddress...")

        val socketChannel = serverSocket.accept()

        logger.info("Connection accepted")

        val inputStream = Channels.newInputStream(socketChannel)
        val outputStream = Channels.newOutputStream(socketChannel)
        val traceWriter = PrintWriter(System.out)

//        val launcher = Launcher.createLauncher<LanguageClient>(
//                LanguageServerEndpoint(server),
//                LanguageClient::class.java,
//                inputStream,
//                outputStream,
//                true,
//                traceWriter)
        val launcher = LSPLauncher.createServerLauncher(
                server,
                inputStream,
                outputStream,
                true,
                traceWriter)

        if (server is LanguageClientAware) {
            val client = launcher.remoteProxy
            server.connect(client)
        }

        return launcher
    }
}