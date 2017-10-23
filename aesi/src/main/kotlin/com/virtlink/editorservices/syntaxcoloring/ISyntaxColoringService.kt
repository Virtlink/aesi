package com.virtlink.editorservices.syntaxcoloring

import com.virtlink.editorservices.ICancellationToken
import com.virtlink.editorservices.IDocument
import com.virtlink.editorservices.IProject
import com.virtlink.editorservices.Span

/**
 * Colors (part of) a document.
 */
interface ISyntaxColoringService {

    /**
     * Colors (part of) a document.
     *
     * @param project The project that contains the document.
     * @param document The document to color.
     * @param span The area of the document to color.
     * @param cancellationToken The cancellation token; or `null` when not supported.
     * @return A list of tokens.
     */
    fun getTokens(
            project: IProject,
            document: IDocument,
            span: Span,
            cancellationToken: ICancellationToken?)
            : List<IToken>
}