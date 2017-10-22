package com.virtlink.editorservices.intellij

import com.google.inject.AbstractModule
import com.google.inject.Singleton
import com.intellij.psi.tree.IFileElementType
import com.google.inject.assistedinject.FactoryModuleBuilder
import com.intellij.lexer.Lexer
import com.virtlink.editorservices.content.IContentManager
import com.virtlink.editorservices.intellij.content.DocumentContentManager
import com.virtlink.editorservices.intellij.content.IntellijContentSource
import com.virtlink.editorservices.intellij.content.TextContentSource
import com.virtlink.editorservices.intellij.psi.*
import com.virtlink.editorservices.intellij.referenceresoluton.AesiReferenceProvider
import com.virtlink.editorservices.intellij.syntaxcoloring.AesiLexer
import com.virtlink.editorservices.intellij.syntaxcoloring.ILexerFactory
import com.virtlink.editorservices.intellij.syntaxcoloring.ScopeManager


abstract class AesiModule : AbstractModule() {
    override fun configure() {
        bind(DocumentManager::class.java).`in`(Singleton::class.java)
        bind(ProjectManager::class.java).`in`(Singleton::class.java)
        bind(AesiTokenTypeManager::class.java).`in`(Singleton::class.java)
        bind(AesiElementTypeManager::class.java).`in`(Singleton::class.java)
        bind(ScopeManager::class.java).`in`(Singleton::class.java)
        bind(IFileElementType::class.java).to(AesiFileElementType::class.java).`in`(Singleton::class.java)
        bind(AesiReferenceProvider::class.java).`in`(Singleton::class.java)
        bind(IContentManager::class.java).to(DocumentContentManager::class.java).`in`(Singleton::class.java)
        bind(IntellijContentSource::class.java).`in`(Singleton::class.java)
        bind(TextContentSource::class.java).`in`(Singleton::class.java)
        install(FactoryModuleBuilder()
                .implement(Lexer::class.java, AesiLexer::class.java)
                .build(ILexerFactory::class.java))
        install(FactoryModuleBuilder()
                .implement(AesiAstBuilder::class.java, AesiAstBuilder::class.java)
                .build(IAstBuilderFactory::class.java))
        bindCodeCompletion()
        bindSyntaxColoring()
    }

    protected open fun bindCodeCompletion() {
    }

    protected open fun bindSyntaxColoring() {

    }
}