package com.virtlink.pie

import com.google.inject.Binder
import com.google.inject.assistedinject.FactoryModuleBuilder
import com.google.inject.multibindings.MapBinder
import com.virtlink.editorservices.codecompletion.ICodeCompletionService
<<<<<<< Updated upstream
import com.virtlink.editorservices.documents.*
import com.virtlink.pie.codecompletion.PieCodeCompletionBuilder
=======
import com.virtlink.dummy.DummyCodeCompletionBuilder
>>>>>>> Stashed changes
import com.virtlink.pie.codecompletion.PieCodeCompletionService
import mb.pie.runtime.builtin.util.LoggerBuildReporter
import mb.pie.runtime.core.*
import mb.pie.runtime.core.impl.BuildCache
import mb.pie.runtime.core.impl.BuildManagerImpl
import mb.pie.runtime.core.impl.MapBuildCache

open class PieImplModule : PieModule() {

    override fun configure(binder: Binder) {
        super.configure(binder)
        binder.install(FactoryModuleBuilder()
                .implement(IDocumentContent::class.java, DocumentContent::class.java)
                .build(IDocumentContentFactory::class.java))
        binder.bind<IDocumentContentManager>().toSingleton<DocumentContentManager>()
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