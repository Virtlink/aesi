package com.virtlink.editorservices.lsp

import com.google.inject.AbstractModule
import com.google.inject.Singleton
import com.google.inject.assistedinject.FactoryModuleBuilder
import com.virtlink.editorservices.ISessionManager
import com.virtlink.editorservices.SessionManager
import com.virtlink.editorservices.lsp.server.AesiLanguageServer
import org.eclipse.lsp4j.services.LanguageServer
import com.virtlink.editorservices.lsp.content.RemoteContentSource
import com.virtlink.editorservices.lsp.content.DocumentContentManager
import com.virtlink.editorservices.lsp.resources.LspResourceManager
import com.virtlink.editorservices.resources.IResourceManager


open class AesiLspModule : AbstractModule() {
    override fun configure() {
        bind(LanguageServer::class.java).to(AesiLanguageServer::class.java).`in`(Singleton::class.java)
        bind(SessionManager::class.java).`in`(Singleton::class.java)
        bind(ISessionManager::class.java).to(SessionManager::class.java)

        bind(RemoteContentSource::class.java).`in`(Singleton::class.java)
        bind(DocumentContentManager::class.java).`in`(Singleton::class.java)
        bind(LspResourceManager::class.java).`in`(Singleton::class.java)
        bind(IResourceManager::class.java).to(LspResourceManager::class.java)
    }
}