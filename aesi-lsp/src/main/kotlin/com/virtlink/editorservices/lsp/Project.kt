package com.virtlink.editorservices.lsp

import com.virtlink.editorservices.IProject
import java.io.Serializable
import java.net.URI

/**
 * A project.
 *
 * @property uri The URI of the project.
 */
data class Project(override val uri: URI): IProject, Serializable {

    override fun toString(): String
            = this.uri.toString()

}