package com.virtlink.paplj.syntaxcoloring

import com.virtlink.editorservices.ICancellationToken
import com.virtlink.editorservices.IDocument
import com.virtlink.editorservices.IProject
import com.virtlink.editorservices.Span
import com.virtlink.editorservices.syntaxcoloring.ISyntaxColorer
import com.virtlink.editorservices.syntaxcoloring.IToken
import com.virtlink.editorservices.syntaxcoloring.Token

class DummySyntaxColorer : ISyntaxColorer {
    override fun highlight(project: IProject, document: IDocument, span: Span, cancellationToken: ICancellationToken?): List<IToken> {
        return listOf(
                Token(Span(2, 6), "keyword"),
                Token(Span(12, 18), "keyword")
        )
    }

}