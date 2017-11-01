package com.virtlink.pie

import com.google.inject.Binder
import com.google.inject.multibindings.MapBinder
import com.google.inject.name.Names
import com.virtlink.dummy.DummyCodeCompletionBuilder
import com.virtlink.pie.codecompletion.PieCodeCompletionService
import mb.pie.runtime.core.UBuilder
import mb.pie.runtime.core.asSingleton
import mb.pie.runtime.core.bind
import mb.pie.runtime.core.to

class PieImplModule: mb.pie.runtime.core.PieModule() {

    override fun Binder.bindBuilders(builders: MapBinder<String, UBuilder>) {
        bindBuilder<DummyCodeCompletionBuilder>(builders, Names.named(PieCodeCompletionService.id), DummyCodeCompletionBuilder.id)
    }

}

inline fun <reified B : UBuilder> Binder.bindBuilder(
        builderBinder: MapBinder<String, UBuilder>,
        annotation: Annotation,
        builderId: String) {

    bind<B>().asSingleton()
    bindConstant().annotatedWith(annotation).to(builderId)
//    this.bind(String::class.java)
//            .annotatedWith(annotation)
//            .toInstance(builderId)
    builderBinder.addBinding(builderId).to<B>()
}