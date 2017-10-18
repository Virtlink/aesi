package com.virtlink.editorservices.lsp.server

import org.eclipse.lsp4j.jsonrpc.Launcher
import org.eclipse.lsp4j.services.LanguageClient

interface ILanguageServerLauncher {
    fun launch()
}