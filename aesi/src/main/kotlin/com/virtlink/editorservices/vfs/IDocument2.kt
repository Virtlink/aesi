package com.virtlink.editorservices.vfs

import com.virtlink.editorservices.Offset
import com.virtlink.editorservices.Position
import java.io.LineNumberReader
import java.net.URI

/**
 * A snapshot of a document.
 */
interface IDocument2 {

    /**
     * Gets the URI of the document.
     */
    val uri: URI

    /**
     * Gets the version of the document.
     */
    val version: Int

    /**
     * Gets the full text of the document.
     */
    val text: String

    /**
     * Gets the length of the document, in characters.
     */
    val length: Int

    /**
     * Gets the lines of the document.
     */
    val lines: ILineList

}