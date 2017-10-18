package com.virtlink.editorservices.documents

import com.virtlink.editorservices.IDocument

/**
 * Manages the content of opened documents.
 */
interface IDocumentContentManager {

    /**
     * Gets the latest content for the specified opened document.
     *
     * @param document The document.
     * @return The latest document content, or null when the document was not opened.
     */
    fun getLatestContent(document: IDocument): IDocumentContent?

    /**
     * Opens the specified document.
     *
     * If the document is already opened, nothing happens.
     *
     * @param document The document being opened.
     */
    fun openDocument(document: IDocument): IDocumentContent

    /**
     * Closes the specified document.
     *
     * If the document is already closed, nothing happens.
     *
     * @param document The document being closed.
     */
    fun closeDocument(document: IDocument)

    /**
     * Updates the content of the specified document.
     *
     * If the document is not open, nothing happens.
     *
     * @param document The document to update.
     * @param content The new document content.
     */
    fun updateDocument(document: IDocument, content: IDocumentContent)

}