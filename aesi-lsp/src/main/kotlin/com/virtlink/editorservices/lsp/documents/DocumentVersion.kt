package com.virtlink.editorservices.lsp.documents

import com.virtlink.editorservices.IDocument
import com.virtlink.editorservices.IProject
import java.net.URI

data class DocumentV(override val project: IProject, override val uri: URI, val version: Int?) : IDocument {

    override fun toString(): String
            = if (version != null) "$relativeUri#$version" else "$relativeUri"

    private val relativeUri
        get() = project.uri.relativize(this.uri)

}