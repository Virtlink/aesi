package com.virtlink.paplj.syntaxcoloring

import com.virtlink.editorservices.ICancellationToken
import com.virtlink.editorservices.IDocument
import com.virtlink.editorservices.IProject
import com.virtlink.editorservices.Span
import com.virtlink.editorservices.syntaxcoloring.ISyntaxColorer
import com.virtlink.editorservices.syntaxcoloring.IToken
import com.virtlink.paplj.syntax.testBaseListener
import com.virtlink.paplj.syntax.testLexer
import com.virtlink.paplj.syntax.testParser
import org.antlr.v4.runtime.ANTLRInputStream
import org.antlr.v4.runtime.CommonTokenStream
import org.antlr.v4.runtime.ParserRuleContext
import org.antlr.v4.runtime.tree.ErrorNode
import org.antlr.v4.runtime.tree.ParseTreeListener
import org.antlr.v4.runtime.tree.TerminalNode

class AntlrSyntaxColorizer: ISyntaxColorer {
    override fun highlight(project: IProject, document: IDocument, span: Span, cancellationToken: ICancellationToken?): List<IToken> {
        val tokens = mutableListOf<IToken>()

        val input = ANTLRInputStream(document.text)
        val lexer = testLexer(input)
        val tokenStream = CommonTokenStream(lexer)
        val parser = testParser(tokenStream)
        parser.addParseListener(object : ParseTreeListener {
            override fun enterEveryRule(ctx: ParserRuleContext?) {
                System.out.println(ctx)
            }

            override fun exitEveryRule(ctx: ParserRuleContext?) {
                System.out.println(ctx)
            }

            override fun visitErrorNode(node: ErrorNode?) {
                System.out.println(node)
            }

            override fun visitTerminal(node: TerminalNode?) {
                System.out.println(node)
            }
        })
        parser.r()

        return tokens
    }
}