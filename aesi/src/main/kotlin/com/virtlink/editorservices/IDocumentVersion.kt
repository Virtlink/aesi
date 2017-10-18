package com.virtlink.editorservices

/**
 * A version of a document.
 */
@Deprecated("")
interface IDocumentVersion {

    /**
     * Gets the document to which this version belongs.
     */
    val document: IDocument

    /**
     * Gets the document's version.
     *
     * A higher number indicates a newer version.
     */
    val version: Int

}