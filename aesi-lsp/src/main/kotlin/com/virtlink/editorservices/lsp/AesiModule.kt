package com.virtlink.editorservices.lsp

import com.google.inject.AbstractModule
import com.google.inject.Singleton
import com.google.inject.assistedinject.FactoryModuleBuilder
import com.virtlink.editorservices.ISessionManager
import com.virtlink.editorservices.SessionManager
import com.virtlink.editorservices.lsp.content.FileSystemContentSource
import com.virtlink.editorservices.lsp.server.AesiLanguageServer
import org.eclipse.lsp4j.services.LanguageServer
import com.virtlink.editorservices.lsp.content.RemoteContentSource
import com.virtlink.editorservices.lsp.content.DocumentContentManager


open class AesiModule : AbstractModule() {
    override fun configure() {
        bind(LanguageServer::class.java).to(AesiLanguageServer::class.java).`in`(Singleton::class.java)
        bind(ISessionManager::class.java).to(SessionManager::class.java).`in`(Singleton::class.java)
        bind(DocumentContentManager::class.java).`in`(Singleton::class.java)
        bind(RemoteContentSource::class.java).`in`(Singleton::class.java)
        bind(FileSystemContentSource::class.java).`in`(Singleton::class.java)

        bind(ProjectManager::class.java).`in`(Singleton::class.java)
        install(FactoryModuleBuilder()
                .implement(DocumentManager::class.java, DocumentManager::class.java)
                .build(IDocumentManagerFactory::class.java))
    }
}