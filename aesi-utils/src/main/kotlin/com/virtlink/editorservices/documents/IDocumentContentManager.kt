package com.virtlink.editorservices.documents

import com.virtlink.editorservices.IDocument

/**
 * Manages the content of opened documents.
 */
interface IDocumentContentManager {

    /**
     * Gets the content for the specified opened document.
     *
     * @param document The document.
     * @return The document content, or null when the document was not opened.
     */
    fun getContent(document: IDocument): IDocumentContent?

    /**
     * Opens the specified document.
     *
     * @param document The document being opened.
     */
    fun openDocument(document: IDocument): IDocumentContent

    /**
     * Closes the specified document.
     *
     * @param document The document being closed.
     */
    fun closeDocument(document: IDocument)

}