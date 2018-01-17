package com.virtlink.pie.lsp

import com.google.inject.Guice
import com.virtlink.editorservices.lsp.CommandLineArgs
import com.virtlink.editorservices.lsp.server.SocketLanguageServerLauncher
import com.virtlink.editorservices.lsp.server.StdioLanguageServerLauncher
import com.virtlink.logging.LogLevel
import com.virtlink.logging.LogWriter
import com.virtlink.logging.isLogEnabled
import org.eclipse.lsp4j.services.LanguageServer
import org.slf4j.LoggerFactory
import java.io.PrintWriter

/**
 * The main entry point of the language server.
 */
fun main(rawArgs: Array<String>) {
    val injector = Guice.createInjector(PieLspModule())

    val args = CommandLineArgs(rawArgs)
    val server = injector.getInstance(LanguageServer::class.java)
    val port = args.port
    val launcher = if (port != null) {
        SocketLanguageServerLauncher(
                port,
                server)
    } else {
        StdioLanguageServerLauncher(
                server)
    }
    val traceWriter = if (args.trace) createTraceWriter() else null
    launcher.launch(traceWriter)
}

private fun createTraceWriter(): PrintWriter? {
    val logger = LoggerFactory.getLogger("JSON")
    val logLevel = LogLevel.Trace
    if (!logger.isLogEnabled(logLevel)) {
        // If the logger isn't enabled to write our messages,
        // we don't even have to create the logger.
        return null
    }
    return PrintWriter(LogWriter(logger, logLevel))
}