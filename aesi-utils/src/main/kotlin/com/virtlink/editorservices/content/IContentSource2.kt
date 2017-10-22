package com.virtlink.editorservices.content

import com.virtlink.editorservices.IDocument

/**
 * A content source is the source of document versions and content for one or more documents.
 */
interface IContentSource2<in T: IDocument> {

    /**
     * Gets the latest content of a document.
     *
     * @param document The document.
     * @return The latest content of the document.
     */
    fun getLatest(document: T): IContent

}