package com.virtlink.pie

import com.google.inject.Binder
import com.google.inject.multibindings.MapBinder
import com.virtlink.editorservices.codecompletion.ICodeCompletionService
import com.virtlink.pie.codecompletion.PieCodeCompletionBuilder
import com.virtlink.pie.codecompletion.PieCodeCompletionService
import mb.pie.runtime.core.*

open class PieImplModule : PieModule() {

    override fun configure(binder: Binder) {
        super.configure(binder)
        binder.bindCodeCompletion()
        binder.bindPie()
    }

    open fun Binder.bindPie() {
        bind<IBuildManagerProvider>().to<BuildManagerProviderImpl>().asSingleton()
    }

    open fun Binder.bindCodeCompletion() {
        bind<ICodeCompletionService>().toSingleton<PieCodeCompletionService>()
    }

    override fun Binder.bindBuilders(builders: MapBinder<String, UBuilder>) {
        bindBuilder<PieCodeCompletionBuilder>(builders, PieCodeCompletionBuilder.id)
    }
}