package com.virtlink.editorservices.lsp

import com.google.inject.Guice
import org.eclipse.lsp4j.services.LanguageServer
import org.slf4j.LoggerFactory

/**
 * The main entry point of the language server.
 */
fun main(rawArgs: Array<String>) {
    var logger = LoggerFactory.getLogger("com.virtlink.editorservices.lsp.Program")

    logger.info("Starting...")

    val injector = Guice.createInjector(AesiModule())

    val args = CommandLineArgs(rawArgs)
    val server = injector.getInstance(LanguageServer::class.java)

    val launcher = SocketLanguageServerLauncher(
            args.port,
            server)
            .createLauncher()

    launcher.startListening()

    logger.info("Exiting")
}