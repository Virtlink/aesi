package com.virtlink.editorservices.intellij

import com.intellij.openapi.module.Module
import java.net.URI

class IntellijProject(val intellijModule: Module) {

    override val uri: URI
        // TODO Test this
        get() = URI(intellijModule.moduleFilePath)

}