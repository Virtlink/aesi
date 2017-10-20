package com.virtlink.editorservices

import java.io.Serializable
import java.net.URI

/**
 * A document.
 */
interface IDocument : Serializable {

    /**
     * Gets the URI of the document.
     */
    val uri: URI

}