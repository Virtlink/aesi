package com.virtlink.editorservices.lsp.server

import com.google.inject.Inject
import com.google.inject.assistedinject.Assisted
import org.eclipse.lsp4j.launch.LSPLauncher
import org.eclipse.lsp4j.services.LanguageClientAware
import org.eclipse.lsp4j.services.LanguageServer
import org.slf4j.LoggerFactory
import java.io.PrintWriter
import java.net.InetSocketAddress
import java.nio.channels.AsynchronousServerSocketChannel
import java.nio.channels.AsynchronousSocketChannel
import java.nio.channels.Channels
import java.util.concurrent.TimeoutException

class SocketLanguageServerLauncher @Inject constructor(
        @Assisted private val port: Int,
        private val server: LanguageServer)
    : ILanguageServerLauncher {

    val logger = LoggerFactory.getLogger(SocketLanguageServerLauncher::class.java)

    override fun launch(traceWriter: PrintWriter?) {
        val serverSocket = AsynchronousServerSocketChannel.open()
        val socketAddress = InetSocketAddress("localhost", this.port)
        serverSocket.bind(socketAddress)

        logger.info("Listening to $socketAddress...")

        val socketChannelFuture = serverSocket.accept()
        val socketChannel: AsynchronousSocketChannel = try { socketChannelFuture.get(/* TODO: Time-out? */) }
        catch (e: TimeoutException) {
            logger.info("No client connected to the server before the time-out. Exiting.")
            System.exit(1)
            return
        }

        logger.info("Connection accepted")

        val inputStream = Channels.newInputStream(socketChannel)
        val outputStream = Channels.newOutputStream(socketChannel)

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

        launcher.startListening()
    }
}