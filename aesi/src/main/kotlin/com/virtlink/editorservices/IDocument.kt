package com.virtlink.editorservices

import java.io.Serializable
import java.net.URI

/**
 * A document.
 */
interface IDocument : Serializable {

    /**
     * Gets the project to which this document belongs.
     */
    val project: IProject

    /**
     * Gets the document's URI.
     */
    val uri: URI

}