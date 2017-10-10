package com.virtlink.editorservices.lsp

import com.google.inject.Inject
import com.google.inject.assistedinject.Assisted
import org.eclipse.lsp4j.jsonrpc.Launcher
import org.eclipse.lsp4j.launch.LSPLauncher
import org.eclipse.lsp4j.services.LanguageClient
import org.eclipse.lsp4j.services.LanguageClientAware
import org.eclipse.lsp4j.services.LanguageServer
import java.io.PrintWriter
import java.net.InetSocketAddress
import java.nio.channels.Channels
import java.nio.channels.ServerSocketChannel

class StdioLanguageServerLauncher @Inject constructor(
        @Assisted val port: Int,
        private val server: LanguageServer)
    : ILanguageServerLauncher {

    override fun launch()  {
        val traceWriter = PrintWriter(System.err)
        System.setOut(System.err)

        val launcher = LSPLauncher.createServerLauncher(
                server,
                System.`in`,
                System.`out`,
                true,
                traceWriter)

        if (server is LanguageClientAware) {
            val client = launcher.remoteProxy
            server.connect(client)
        }

        launcher.startListening()
    }
}