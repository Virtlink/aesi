package com.virtlink.editorservices.syntaxhighlighting

import com.virtlink.editorservices.ICancellationToken
import com.virtlink.editorservices.IDocument
import com.virtlink.editorservices.IProject
import com.virtlink.editorservices.Span

interface ISyntaxHighlighter {
    /**
     *
     * @param project The project.
     * @param document The document to parse.
     * @param text The full document text.
     * @param span The area of the document to parse.
     * @param cancellationToken The cancellation token; or null.
     */
    fun highlight(project: IProject, document: IDocument, text: String, span: Span, cancellationToken: ICancellationToken?): List<IToken>
}