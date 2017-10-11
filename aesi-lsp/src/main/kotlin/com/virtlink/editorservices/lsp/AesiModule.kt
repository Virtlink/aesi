package com.virtlink.editorservices.lsp

import com.google.inject.Binder
import com.google.inject.AbstractModule
import com.google.inject.Singleton
import org.eclipse.lsp4j.services.LanguageServer

open class AesiModule : AbstractModule() {
    override fun configure() {
        bind(LanguageServer::class.java).to(AesiLanguageServer::class.java).`in`(Singleton::class.java)
//        bind(DocumentManager::class.java).`in`(Singleton::class.java)
        bind(ProjectManager::class.java).`in`(Singleton::class.java)
    }
}