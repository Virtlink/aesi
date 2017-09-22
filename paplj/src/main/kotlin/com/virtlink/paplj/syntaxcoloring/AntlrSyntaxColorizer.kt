package com.virtlink.paplj.syntaxcoloring

import com.virtlink.editorservices.ICancellationToken
import com.virtlink.editorservices.IDocument
import com.virtlink.editorservices.IProject
import com.virtlink.editorservices.Span
import com.virtlink.editorservices.syntaxcoloring.ISyntaxColorer
import com.virtlink.editorservices.syntaxcoloring.IToken
import com.virtlink.editorservices.syntaxcoloring.Token
import com.virtlink.paplj.syntax.PapljLexer
import org.antlr.v4.runtime.ANTLRInputStream

class AntlrSyntaxColorizer: ISyntaxColorer {
    override fun highlight(project: IProject, document: IDocument, span: Span, cancellationToken: ICancellationToken?): List<IToken> {
        val tokens = mutableListOf<IToken>()

        val input = ANTLRInputStream(document.text)
        val lexer = PapljLexer(input)
        var token = lexer.nextToken()
        while (token.type != org.antlr.v4.runtime.Token.EOF) {
            val scope = getTokenScope(token)
            val startOffset = token.startIndex
            val endOffset = token.stopIndex + 1
            tokens.add(Token(Span(startOffset, endOffset), scope))

            token = lexer.nextToken()
        }

        return tokens
    }

    private val keywords = arrayOf("PROGRAM", "RUN", "IMPORT", "CLASS", "EXTENDS", "IF", "ELSE", "LET", "IN",
            "AS", "TRUE", "FALSE", "THIS", "NULL", "NEW")
    private val operators = arrayOf("EQ", "NEQ", "LTE", "GTE", "LT", "GT",
            "OR", "AND", "ASSIGN", "PLUS", "MIN", "MUL", "DIV", "NOT")


    private fun getTokenScope(token: org.antlr.v4.runtime.Token): String {
        val tokenName = if (token.type > 0 && token.type <= PapljLexer.ruleNames.size) PapljLexer.ruleNames[token.type - 1] else null
        return when (tokenName) {
            in keywords -> "keyword"
            in operators -> "keyword.operator"
            "LBRACE", "RBRACE" -> "meta.braces"
            "LPAREN", "RPAREN" -> "meta.parens"
            "DOT", "DOTSTAR" -> "punctuation.accessor"
            "COMMA" -> "punctuation.separator"
            "SEMICOLON" -> "punctuation.terminator"
            "ID" -> "entity.name" // Not really correct
            "INT" -> "constant.numeric"
            "COMMENT" -> "comment.block"
            "LINE_COMMENT" -> "comment.line"
            "WS" -> "text.whitespace"
            else -> "invalid.illegal"
        }
    }
}