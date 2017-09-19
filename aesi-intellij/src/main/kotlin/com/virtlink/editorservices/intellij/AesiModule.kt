package com.virtlink.editorservices.intellij

import com.google.inject.AbstractModule
import com.google.inject.Binder
import com.google.inject.Module



abstract class AesiModule : AbstractModule() {
    override fun configure() {
        bind(DocumentManager::class.java)
        bind(ProjectManager::class.java)

        bindCodeCompletion()
        bindSyntaxColoring()
    }

    protected open fun bindCodeCompletion() {
    }

    protected open fun bindSyntaxColoring() {

    }
}