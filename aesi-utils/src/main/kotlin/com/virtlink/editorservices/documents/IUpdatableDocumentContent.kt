package com.virtlink.editorservices.documents

import com.virtlink.editorservices.Span

/**
 * An updatable document's content.
 */
interface IUpdatableDocumentContent : IDocumentContent {
    /**
     * Updates the document to include the specified change.
     *
     * @param span The span of changed text.
     * @param newText The next text of the specified span,
     * which may be an empty string when text was only removed.
     */
    fun update(span: Span, newText: String)
}