package org.metaborg.paplj.syntaxhighlighting

import com.virtlink.editorservices.ICancellationToken
import com.virtlink.editorservices.IDocument
import com.virtlink.editorservices.IProject
import com.virtlink.editorservices.Span
import com.virtlink.editorservices.syntaxhighlighting.ISyntaxHighlighter
import com.virtlink.editorservices.syntaxhighlighting.IToken
import com.virtlink.editorservices.syntaxhighlighting.Token

class PapljSyntaxHighlighter : ISyntaxHighlighter {
    override fun highlight(project: IProject, document: IDocument, text: String, span: Span, cancellationToken: ICancellationToken?): List<IToken> {
        return listOf(
                Token(Span(0, 2, 0, 6), "keyword"),
                Token(Span(2, 2, 2, 6), "keyword")
        )
    }
}