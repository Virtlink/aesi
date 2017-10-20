package com.virtlink.pie.lsp

import com.google.inject.Guice
import com.virtlink.editorservices.lsp.CommandLineArgs
import com.virtlink.editorservices.lsp.server.SocketLanguageServerLauncher
import org.eclipse.lsp4j.services.LanguageServer

/**
 * The main entry point of the language server.
 */
fun main(rawArgs: Array<String>) {
    val injector = Guice.createInjector(PieLspModule())

    val args = CommandLineArgs(rawArgs)
    val server = injector.getInstance(LanguageServer::class.java)

    SocketLanguageServerLauncher(
            args.port,
            server)
            .launch()
}