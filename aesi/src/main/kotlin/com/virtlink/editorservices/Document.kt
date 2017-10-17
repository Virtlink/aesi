package com.virtlink.editorservices

import java.io.Serializable
import java.net.URI

/**
 * A document.
 *
 * @property uri The URI.
 */
@Deprecated("Removed")
data class Document(
        val project: Project,
        val uri: URI
) : Serializable {

    override fun toString(): String
            = this.project.uri.relativize(this.uri).toString()

}