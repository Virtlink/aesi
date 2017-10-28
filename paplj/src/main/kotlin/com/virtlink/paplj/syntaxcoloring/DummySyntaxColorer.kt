package com.virtlink.paplj.syntaxcoloring

import com.virtlink.editorservices.*
import com.virtlink.editorservices.syntaxcoloring.ISyntaxColoringService
import com.virtlink.editorservices.syntaxcoloring.IToken
import com.virtlink.editorservices.syntaxcoloring.Token
import java.net.URI

class DummySyntaxColorer : ISyntaxColoringService {
    override fun getTokens(document: URI, span: Span, cancellationToken: ICancellationToken?): List<IToken> {
        return listOf(
                Token(Span.fromLength(2, 6), "keyword"),
                Token(Span.fromLength(12, 4), "keyword")
        )
    }

}