package com.virtlink.editorservices.lsp.server

import com.virtlink.logging.LogWriter
import org.slf4j.LoggerFactory
import java.io.PrintWriter

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