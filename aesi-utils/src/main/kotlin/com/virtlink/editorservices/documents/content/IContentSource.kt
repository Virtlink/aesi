package com.virtlink.editorservices.documents.content

import com.virtlink.editorservices.IDocument

/**
 * A source of document content.
 */
interface IContentSource {

    /**
     * Gets the document for which this is the content source.
     */
    val document: IDocument

    /**
     * Gets the latest document content for the document.
     *
     * @return The document content.
     */
    fun getLatestContent(): IDocumentContent

    /**
     * Invalidates any cached version(s) of the document's content.
     */
    fun invalidate()

}