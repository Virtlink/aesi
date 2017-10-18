package com.virtlink.pie

import com.google.inject.Binder
import com.virtlink.editorservices.codecompletion.ICodeCompletionService
import com.virtlink.pie.codecompletion.PieCodeCompletionService
import mb.pie.runtime.core.*
import mb.pie.runtime.core.impl.cache.MapBuildCache

open class PieImplModule : PieModule() {

    override fun configure(binder: Binder) {
        super.configure(binder)
        binder.bindCodeCompletion()
        binder.bindPie()
    }

    open fun Binder.bindPie() {
        bind<IBuildManagerProvider>().to<BuildManagerProviderImpl>().asSingleton()
        bind<BuildCache>().to<MapBuildCache>()
    }

    open fun Binder.bindCodeCompletion() {
        bind<ICodeCompletionService>().toSingleton<PieCodeCompletionService>()
    }
}