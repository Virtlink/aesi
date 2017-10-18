package com.virtlink.editorservices.lsp.server

import com.google.inject.Inject
import org.eclipse.lsp4j.launch.LSPLauncher
import org.eclipse.lsp4j.services.LanguageClientAware
import org.eclipse.lsp4j.services.LanguageServer

class StdioLanguageServerLauncher @Inject constructor(
        private val server: LanguageServer)
    : LanguageServerLauncherBase() {

    override fun launch()  {
        val traceWriter = createTraceWriter()
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