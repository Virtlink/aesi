package com.virtlink.editorservices.lsp.server

import java.io.PrintWriter

interface ILanguageServerLauncher {
    fun launch(traceWriter: PrintWriter?)
}