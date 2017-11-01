package com.virtlink.pie.intellij

import com.google.inject.Guice
import com.google.inject.Injector
import com.intellij.openapi.diagnostic.Logger
import com.virtlink.editorservices.intellij.AesiPlugin

class PiePlugin(injector: Injector) : AesiPlugin(injector) {

    companion object {
        @JvmStatic private val LOG = Logger.getInstance(PiePlugin::class.java)

        private lateinit var plugin: PiePlugin

        /**
         * Returns the shared plugin instance.
         *
         * @return The shared plugin instance.
         */
        @JvmStatic val default get() = this.plugin

        @JvmStatic fun init() {
            LOG.debug("Starting PIE plugin.")
            val injector = Guice.createInjector(PieIntellijModule())
            plugin = PiePlugin(injector)
        }
    }

    init {
        LOG.info("PIE plugin created.")
    }

    override fun start() {
        super.start()
        LOG.info("PIE plugin started.")
    }
}
