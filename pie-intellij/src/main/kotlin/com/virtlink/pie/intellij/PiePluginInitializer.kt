package com.virtlink.pie.intellij

import com.intellij.ide.ApplicationLoadListener
import com.intellij.openapi.application.Application
import com.intellij.openapi.diagnostic.Logger
import com.virtlink.editorservices.intellij.referenceresoluton.AesiReferenceProvider

class PiePluginInitializer : ApplicationLoadListener {

    override fun beforeApplicationLoaded(application: Application, configPath: String) {
        PiePlugin.init()
        PiePlugin.default.start()
    }
}