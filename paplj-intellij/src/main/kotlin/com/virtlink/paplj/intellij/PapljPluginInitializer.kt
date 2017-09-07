package com.virtlink.paplj.intellij

import com.intellij.ide.ApplicationLoadListener
import com.intellij.openapi.application.Application

class PapljPluginInitializer : ApplicationLoadListener {

    override fun beforeApplicationLoaded(application: Application, configPath: String) {
        PapljPlugin.getDefault().start()
    }
}