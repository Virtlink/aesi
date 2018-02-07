package com.virtlink.paplj.syntaxcoloring

import com.google.inject.Inject
import com.virtlink.editorservices.*
import com.virtlink.editorservices.resources.IResourceManager
import com.virtlink.editorservices.syntaxcoloring.*
import com.virtlink.logging.logger
import com.virtlink.paplj.syntax.PapljAntlrLexer
import org.antlr.v4.runtime.ANTLRInputStream
import java.net.URI

class AntlrSyntaxColorizer @Inject constructor(
        private val resourceManager: IResourceManager)
    : ISyntaxColoringService {

    @Suppress("PrivatePropertyName")
    private val LOG by logger()

    override fun configure(configuration: ISyntaxColoringConfiguration) {
        // Nothing to do.
    }

    override fun getSyntaxColoringInfo(document: URI, span: Span, cancellationToken: ICancellationToken?): ISyntaxColoringInfo? {
        val tokens = mutableListOf<IToken>()

        val content = this.resourceManager.getContent(document)
        if (content == null) {
            LOG.warn("$document: Could not get content.")
            return SyntaxColoringInfo(emptyList())
        }

        val input = ANTLRInputStream(content.text)
        val lexer = PapljAntlrLexer(input)
        var token = lexer.nextToken()
        while (token.type != org.antlr.v4.runtime.Token.EOF) {
            val scope = getTokenScope(token)
            val startOffset = token.startIndex
            val endOffset = token.stopIndex + 1
            tokens.add(Token(Span(startOffset.toLong(), endOffset.toLong()), ScopeNames(scope)))

            token = lexer.nextToken()
        }

        return SyntaxColoringInfo(tokens)
    }

    private val keywords = arrayOf("PROGRAM", "RUN", "IMPORT", "CLASS", "EXTENDS", "IF", "ELSE", "LET", "IN",
            "AS", "TRUE", "FALSE", "THIS", "NULL", "NEW")
    private val operators = arrayOf("EQ", "NEQ", "LTE", "GTE", "LT", "GT",
            "OR", "AND", "ASSIGN", "PLUS", "MIN", "MUL", "DIV", "NOT")


    private fun getTokenScope(token: org.antlr.v4.runtime.Token): String {
        val tokenName = if (token.type > 0 && token.type <= PapljAntlrLexer.ruleNames.size) PapljAntlrLexer.ruleNames[token.type - 1] else null
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