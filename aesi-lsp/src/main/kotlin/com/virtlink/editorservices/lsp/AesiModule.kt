package com.virtlink.editorservices.lsp

import com.google.inject.Binder
import com.google.inject.AbstractModule
import org.eclipse.lsp4j.services.LanguageServer

class AesiModule : AbstractModule() {
    override fun configure() {
        bind(LanguageServer::class.java).to(AbstractLanguageServer::class.java)
    }
}