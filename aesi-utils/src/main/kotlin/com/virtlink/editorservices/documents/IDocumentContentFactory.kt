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
     * @param updatable Whether the resulting document must be updatable.
     */
    fun create(document: IDocument, updatable: Boolean): IDocumentContent

    /**
     * Creates a new [IUpdatableDocumentContent] object for the specified document.
     *
     * @param document The document.
     */
    fun createUpdatable(document: IDocument): IUpdatableDocumentContent
        = create(document, true) as IUpdatableDocumentContent
}