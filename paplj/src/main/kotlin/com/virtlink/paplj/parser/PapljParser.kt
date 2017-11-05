package com.virtlink.paplj.parser

import com.google.inject.Inject
import com.virtlink.paplj.syntax.PapljAntlrLexer
import com.virtlink.paplj.syntax.PapljAntlrParser
import com.virtlink.terms.ITerm
import com.virtlink.terms.Term
import org.antlr.v4.runtime.ANTLRInputStream
import org.antlr.v4.runtime.CommonTokenStream
import java.io.Reader

/**
 * Parses PAPLJ source code into an AST.
 */
class PapljParser @Inject constructor(
        private val astBuilder: AstBuilder
) {

    fun parse(reader: Reader): ITerm {
        val input = ANTLRInputStream(reader)
        val lexer = PapljAntlrLexer(input)
        val tokens = CommonTokenStream(lexer)
        val parser = PapljAntlrParser(tokens)
        val parseTree = parser.program()
        return this.astBuilder.visit(parseTree)
    }

}