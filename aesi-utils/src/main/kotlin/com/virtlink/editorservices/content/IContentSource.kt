package com.virtlink.editorservices.content

import com.virtlink.editorservices.IDocument

/**
 * A content source is the source of document versions and content for one or more documents.
 */
interface IContentSource {

    /**
     * Gets the latest content of a document.
     *
     * @param document The document.
     * @return The latest content of the document.
     */
    fun getLatest(document: IDocument): IContent

//    /**
//     * Attaches the specified document to this content source
//     * so that its changes are tracked.
//     *
//     * @param document The document to attach.
//     */
//    fun attach(document: IDocument)
//
//    /**
//     * Detaches the specified document from this content source
//     * so that its changes are no longer tracked.
//     *
//     * @param document The document to detach.
//     */
//    fun detach(document: IDocument)

}