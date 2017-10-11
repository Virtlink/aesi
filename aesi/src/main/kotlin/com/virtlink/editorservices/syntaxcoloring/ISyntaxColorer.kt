package com.virtlink.editorservices.syntaxcoloring

import com.virtlink.editorservices.*

@Deprecated("Replaced by ISyntaxColoringService")
interface ISyntaxColorer {
    /**
     * Colorizes (part of) a document.
     *
     * @param project The project.
     * @param document The document to parse.
     * @param span The area of the document to colorize.
     * @param cancellationToken The cancellation token; or null.
     * @return A list of tokens.
     */
    fun highlight(project: IProject, document: IDocument, span: Span, cancellationToken: ICancellationToken?): List<IToken>
}