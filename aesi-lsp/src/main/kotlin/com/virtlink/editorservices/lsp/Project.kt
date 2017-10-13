package com.virtlink.editorservices.lsp

import com.virtlink.editorservices.IProject
import java.net.URI

class Project(override val uri: URI): IProject {

    val documents = DocumentManager(this)

    override fun toString(): String
        = this.uri.toString()

}