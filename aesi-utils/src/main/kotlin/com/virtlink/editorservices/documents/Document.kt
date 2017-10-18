package com.virtlink.editorservices.documents

import com.virtlink.editorservices.IDocument
import com.virtlink.editorservices.IProject
import java.net.URI

class Document(override val project: IProject, override val uri: URI) : IDocument {

    override fun toString(): String
            = project.uri.relativize(this.uri).toString()

}