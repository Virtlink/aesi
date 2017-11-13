package com.virtlink.editorservices.lsp.server

import com.google.inject.Inject
import com.google.inject.assistedinject.Assisted
import org.eclipse.lsp4j.launch.LSPLauncher
import org.eclipse.lsp4j.services.LanguageClientAware
import org.eclipse.lsp4j.services.LanguageServer
import java.io.PrintWriter

class StdioLanguageServerLauncher @Inject constructor(
        private val server: LanguageServer)
    : ILanguageServerLauncher {

    override fun launch(traceWriter: PrintWriter?)  {
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