package com.virtlink.paplj.syntaxcoloring

import com.virtlink.editorservices.*
import com.virtlink.editorservices.syntaxcoloring.*
import java.net.URI

class DummySyntaxColorer : ISyntaxColoringService {

    override fun configure(configuration: ISyntaxColoringConfiguration) {
        // Nothing to do.
    }

    override fun getSyntaxColoringInfo(document: URI, span: Span, cancellationToken: ICancellationToken?): ISyntaxColoringInfo? {
        return SyntaxColoringInfo(listOf(
                Token(Span.fromLength(2, 6), "keyword"),
                Token(Span.fromLength(12, 4), "keyword")
        ))
    }

}