package com.virtlink.paplj.syntaxcoloring

import com.virtlink.editorservices.*
import com.virtlink.editorservices.syntaxcoloring.ISyntaxColoringService
import com.virtlink.editorservices.syntaxcoloring.IToken
import com.virtlink.editorservices.syntaxcoloring.Token

class DummySyntaxColorer : ISyntaxColoringService {
    override fun highlight(project: IProject, document: IDocument, span: Span, cancellationToken: ICancellationToken?): List<IToken> {
        return listOf(
                Token(Span(Offset(2), Offset(6)), "keyword"),
                Token(Span(Offset(12), Offset(18)), "keyword")
        )
    }

}