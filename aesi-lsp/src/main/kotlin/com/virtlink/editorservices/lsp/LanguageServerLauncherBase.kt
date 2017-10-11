package com.virtlink.editorservices.lsp

import com.google.inject.Inject
import com.google.inject.assistedinject.Assisted
import com.virtlink.logging.LogWriter
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

abstract class LanguageServerLauncherBase()
    : ILanguageServerLauncher {

    protected fun createTraceWriter(): PrintWriter? {
        val logger = LoggerFactory.getLogger("JSON")
        if (!logger.isTraceEnabled) {
            // Since we're writing trace messages, if the logger isn't trace enabled
            // we don't even have to create the logger.
            return null
        }
        return PrintWriter(LogWriter(logger, LogWriter.LogLevel.Trace))
    }
}