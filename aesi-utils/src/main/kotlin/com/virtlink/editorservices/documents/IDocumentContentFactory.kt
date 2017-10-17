package com.virtlink.editorservices.documents

import com.virtlink.editorservices.IDocument

/**
 * Factory for [IDocumentContent] objects.
 */
interface IDocumentContentFactory {

    /**
     * Creates a new [IDocumentContent] object for the specified document.
     *
     * @param document The document.
     */
    fun create(document: IDocument): IDocumentContent
}