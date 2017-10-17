package com.virtlink.editorservices.intellij

import com.intellij.openapi.module.Module
import com.virtlink.editorservices.IProject
import java.net.URI

class IntellijProject(val intellijModule: Module) : IProject {

    override val uri: URI
        // TODO Test this
        get() = URI(intellijModule.moduleFilePath)

}