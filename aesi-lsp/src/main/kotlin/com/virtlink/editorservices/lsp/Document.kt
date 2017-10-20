package com.virtlink.editorservices.lsp

import com.virtlink.editorservices.IDocument
import com.virtlink.editorservices.IProject
import java.io.Serializable
import java.net.URI

/**
 * A document.
 *
 * @property uri The URI of the document.
 * @property project The project the document belongs to.
 */
data class Document(override val uri: URI, val project: Project): IDocument, Serializable {

    override fun toString(): String
            = this.project.uri.relativize(this.uri).toString()

}