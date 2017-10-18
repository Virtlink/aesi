package com.virtlink.editorservices

import java.io.Serializable
import java.net.URI

/**
 * A project.
 */
interface IProject : Serializable {

    /**
     * Gets the URI of the project.
     */
    val uri: URI

}