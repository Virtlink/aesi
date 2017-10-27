package com.virtlink.editorservices.syntaxcoloring

import com.virtlink.editorservices.ICancellationToken
import com.virtlink.editorservices.IDocument
import com.virtlink.editorservices.IProject
import com.virtlink.editorservices.Span
import java.net.URI

/**
 * Colors (part of) a document.
 */
interface ISyntaxColoringService {

    /**
     * Colors (part of) a document.
     *
     * @param document The URI of the document to color.
     * @param span The area of the document to color.
     * @param cancellationToken The cancellation token; or `null` when not supported.
     * @return A list of tokens.
     */
    fun getTokens(
            document: URI,
            span: Span,
            cancellationToken: ICancellationToken?)
            : List<IToken>
}