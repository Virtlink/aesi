package com.virtlink.pie

import com.google.inject.AbstractModule
import com.google.inject.Binder
import com.google.inject.Singleton
import com.google.inject.binder.LinkedBindingBuilder
import com.google.inject.multibindings.MapBinder
import com.virtlink.editorservices.AesiBaseModule
import com.virtlink.editorservices.codecompletion.ICodeCompletionService
import com.virtlink.pie.codecompletion.PieCodeCompletionService
import mb.pie.runtime.core.*
import mb.pie.runtime.core.impl.cache.MapBuildCache
import org.slf4j.LoggerFactory

class PieModule: AesiBaseModule() {

    override fun configure() {
        super.configure()
        configurePie()

        requestStaticInjection(DocumentReq::class.java)
    }

    fun configurePie() {
        install(PieImplModule())
        install(mb.log.LogModule(LoggerFactory.getLogger("root")))
        install(mb.vfs.VFSModule())

        bind(IBuildManagerProvider::class.java).to(BuildManagerProviderImpl::class.java).`in`(Singleton::class.java)
        bind(BuildCache::class.java).to(MapBuildCache::class.java)
    }

    override fun configureCodeCompletion() {
        bind(ICodeCompletionService::class.java).to(PieCodeCompletionService::class.java).`in`(Singleton::class.java)
    }

}