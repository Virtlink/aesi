package com.virtlink.editorservices.syntaxcoloring

import com.virtlink.editorservices.ICancellationToken
import com.virtlink.editorservices.Span
import java.net.URI
import com.virtlink.editorservices.resources.IResourceManager

/**
 * Null implementation for when there is no actual implementation available.
 */
class NullSyntaxColoringService(
        private val resourceService: IResourceManager)
    : ISyntaxColoringService {

    override fun getTokens(document: URI, span: Span, cancellationToken: ICancellationToken): List<IToken> {
        val content = this.resourceService.getContent(document) ?: return emptyList()
        return listOf(Token(Span.fromLength(0, content.length), "text"))
    }
}