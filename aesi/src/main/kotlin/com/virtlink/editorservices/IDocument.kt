package com.virtlink.editorservices

import java.net.URI

/**
 * A document.
 */
interface IDocument {

    /**
     * Gets the document's URI.
     */
    val uri: URI

    /**
     * Gets the document's version.
     *
     * A higher number indicates a newer version.
     */
    val version: Int

}