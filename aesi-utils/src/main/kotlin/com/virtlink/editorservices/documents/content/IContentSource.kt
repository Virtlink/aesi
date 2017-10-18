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

    /**
     * Applies updates to the latest content.
     *
     * All changes must be specified in terms of the document content being changed.
     * Changes must not overlap, and be specified in the order they occur in the document.
     *
     * @param changes The changes to apply to the content in parallel.
     * @return The resulting document content; or null when the changes were not applied.
     */
    fun update(changes: List<DocumentChange>): IDocumentContent?
}