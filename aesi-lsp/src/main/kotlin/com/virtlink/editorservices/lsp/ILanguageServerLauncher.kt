package com.virtlink.editorservices.lsp

import org.eclipse.lsp4j.jsonrpc.Launcher
import org.eclipse.lsp4j.services.LanguageClient

interface ILanguageServerLauncher {
    fun createLauncher(): Launcher<LanguageClient>
}