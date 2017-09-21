package com.virtlink.editorservices.intellij

import com.google.inject.AbstractModule
import com.google.inject.Binder
import com.google.inject.Module
import com.google.inject.Singleton
import com.intellij.psi.tree.IFileElementType
import com.virtlink.editorservices.intellij.psi.AesiFileElementType
import com.virtlink.editorservices.intellij.psi.ElementTypeManager


abstract class AesiModule : AbstractModule() {
    override fun configure() {
        bind(DocumentManager::class.java).`in`(Singleton::class.java)
        bind(ProjectManager::class.java).`in`(Singleton::class.java)
        bind(ElementTypeManager::class.java).`in`(Singleton::class.java)
        bind(IFileElementType::class.java).to(AesiFileElementType::class.java).`in`(Singleton::class.java)

        bindCodeCompletion()
        bindSyntaxColoring()
    }

    protected open fun bindCodeCompletion() {
    }

    protected open fun bindSyntaxColoring() {

    }
}