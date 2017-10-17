package com.virtlink.editorservices

import java.net.URI
import java.io.Serializable

/**
 * A project.
 *
 * @property uri The URI of the project.
 */
@Deprecated("Removed")
data class Project(
        val uri: URI
) : Serializable {

    override fun toString(): String
            = this.uri.toString()

}