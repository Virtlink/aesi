package com.virtlink.editorservices.documents

import com.virtlink.editorservices.IDocument

/**
 * Manages the content of documents.
 */
interface IDocumentContentManager {

    /**
     * Gets the content for the specified document.
     *
     * @param document The document.
     * @return The latest document content.
     */
    fun getContent(document: IDocument): IDocumentContent

//    /**
//     * Opens the specified document.
//     *
//     * If the document is already opened, nothing happens.
//     *
//     * @param document The document being opened.
//     */
//    fun openDocument(document: IDocument)
//
//    /**
//     * Closes the specified document.
//     *
//     * If the document is already closed, nothing happens.
//     *
//     * @param document The document being closed.
//     */
//    fun closeDocument(document: IDocument)

}