package com.virtlink.paplj.intellij

import com.google.inject.Guice
import com.google.inject.Injector
import com.intellij.openapi.diagnostic.Logger
import com.virtlink.editorservices.intellij.AesiPlugin

class PapljPlugin(injector: Injector) : AesiPlugin(injector) {

    companion object {
        @JvmStatic private val LOG = Logger.getInstance(PapljPlugin::class.java)

        private lateinit var plugin: PapljPlugin

        /**
         * Returns the shared plugin instance.
         *
         * @return The shared plugin instance.
         */
        @JvmStatic val default get() = this.plugin

        @JvmStatic fun init() {
            LOG.debug("Starting PAPLJ plugin.")
            val injector = Guice.createInjector(PapljIntellijModule())
            plugin = PapljPlugin(injector)
        }
    }

    init {
        LOG.info("PAPLJ plugin created.")
    }

    override fun start() {
        super.start()
        LOG.info("PAPLJ plugin started.")
    }
}