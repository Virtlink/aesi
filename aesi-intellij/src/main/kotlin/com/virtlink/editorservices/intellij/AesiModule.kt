package com.virtlink.editorservices.intellij

import com.google.inject.AbstractModule
import com.google.inject.Binder
import com.google.inject.Module



abstract class AesiModule : AbstractModule() {
    override fun configure() {
        bindCodeCompletion()
    }

    protected open fun bindCodeCompletion() {
    }
}