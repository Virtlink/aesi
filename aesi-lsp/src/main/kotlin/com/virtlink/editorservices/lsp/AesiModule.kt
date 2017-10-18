package com.virtlink.editorservices.lsp

import com.google.inject.AbstractModule
import com.google.inject.Singleton
import com.virtlink.editorservices.lsp.server.AesiLanguageServer
import org.eclipse.lsp4j.services.LanguageServer
import com.google.inject.assistedinject.FactoryModuleBuilder
import com.virtlink.editorservices.documents.DocumentManager
import com.virtlink.editorservices.documents.IDocumentManager
import com.virtlink.editorservices.documents.IDocumentManagerFactory


open class AesiModule : AbstractModule() {
    override fun configure() {
        bind(LanguageServer::class.java).to(AesiLanguageServer::class.java).`in`(Singleton::class.java)
//        bind(DocumentManager::class.java).`in`(Singleton::class.java)
        bind(ProjectManager::class.java).`in`(Singleton::class.java)

        install(FactoryModuleBuilder()
                .implement(IDocumentManager::class.java, DocumentManager::class.java)
                .build(IDocumentManagerFactory::class.java))
    }
}