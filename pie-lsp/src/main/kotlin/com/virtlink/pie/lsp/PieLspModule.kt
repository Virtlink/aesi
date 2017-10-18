package com.virtlink.pie.lsp

import com.google.inject.Binder
import com.google.inject.multibindings.MapBinder
import com.google.inject.name.Names
import com.virtlink.dummy.DummyCodeCompletionBuilder
import com.virtlink.editorservices.lsp.AesiModule
import com.virtlink.pie.PieImplModule
import mb.log.LogModule
import mb.vfs.VFSModule
import org.slf4j.LoggerFactory
import com.virtlink.pie.codecompletion.PieCodeCompletionService
import mb.pie.runtime.core.*

class PieLspModule : PieImplModule() {

    override fun configure(binder: Binder) {
        super.configure(binder)
        binder.install(AesiModule())
        binder.install(VFSModule())
        binder.install(LogModule(LoggerFactory.getLogger("root")))
    }

    override fun Binder.bindBuilders(builders: MapBinder<String, UBuilder>) {
        bindBuilder<DummyCodeCompletionBuilder>(builders, Names.named(PieCodeCompletionService.ID), DummyCodeCompletionBuilder.id)
    }
}

inline fun <reified B : UBuilder> Binder.bindBuilder(
        builderBinder: MapBinder<String, UBuilder>,
        annotation: Annotation,
        builderId: String) {
    bind<B>().asSingleton()
    this.bind(String::class.java)
            .annotatedWith(annotation)
            .toInstance(builderId)
    builderBinder.addBinding(builderId).to<B>()
}