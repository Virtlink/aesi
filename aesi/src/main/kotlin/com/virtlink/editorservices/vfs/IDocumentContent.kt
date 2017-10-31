package com.virtlink.editorservices.vfs

import java.io.Reader
import java.nio.charset.Charset

/**
 * The content of a document.
 */
interface IDocumentContent: IFileContent {

    /**
     * Gets the encoding of the content.
     */
    val encoding: Charset

    /**
     * Opens a reader from which the character content of the document can be read.
     */
    fun getReader(): Reader

}