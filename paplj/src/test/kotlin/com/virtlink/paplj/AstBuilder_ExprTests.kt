package com.virtlink.paplj

import com.virtlink.paplj.parser.AstBuilder
import com.virtlink.paplj.syntax.PapljAntlrLexer
import com.virtlink.paplj.syntax.PapljAntlrParser
import com.virtlink.paplj.terms.*
import com.virtlink.terms.DefaultTermFactory
import com.virtlink.terms.ListTerm
import com.virtlink.terms.StringTerm
import junit.framework.Assert.assertEquals
import org.antlr.v4.runtime.ANTLRInputStream
import org.antlr.v4.runtime.CommonTokenStream
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import java.io.StringReader
import java.util.*

/**
 * Tests the expressions in the [AstBuilder] class.
 */
@RunWith(Parameterized::class)
class AstBuilder_ExprTests(
        private val input: String,
        private val expected: ExprTerm
) {

    companion object {
        @Parameterized.Parameters(name= "{index}: Expr \"{0}\"={1}")
        @JvmStatic fun data() = listOf(
                // Parens
                arrayOf("(a + b) + c", AddTerm(AddTerm(VarTerm(StringTerm("a")), VarTerm(StringTerm("b"))),  VarTerm(StringTerm("c")))),
                arrayOf("a + (b + c)", AddTerm(VarTerm(StringTerm("a")), AddTerm(VarTerm(StringTerm("b")), VarTerm(StringTerm("c"))))),

                // Block
                arrayOf("{ true }", Block2Term(ListTerm(TrueTerm()))),
                arrayOf("{ true; false }", Block2Term(ListTerm(TrueTerm(), FalseTerm()))),
                arrayOf("{ true; false; }", Block2Term(ListTerm(TrueTerm(), FalseTerm()))),

                // Var
                arrayOf("i", VarTerm(StringTerm("i"))),
                arrayOf("xyz", VarTerm(StringTerm("xyz")))

                // Call
                // New
                // Null
                // This
                // Bool
                // Num
                // Member
                // MemberCall
                // Negate
                // Not
                // Cast
                // Multiplicative
                // Additive
                // Compare
                // And
                // Or
                // Assignment
                // Let
                // If
        )
    }

    @Test
    fun test() {
        // Arrange
        val parser = createParser(this.input)
        val termFactory = DefaultTermFactory()
        val builder = AstBuilder(termFactory)
        val parseTree = parser.expr()

        // Act
        val ast = builder.visit(parseTree)

        // Assert
        assertEquals(this.expected, ast)
    }

    private fun createParser(str: String): PapljAntlrParser {
        val reader = StringReader(str)
        val input = ANTLRInputStream(reader)
        val lexer = PapljAntlrLexer(input)
        val tokens = CommonTokenStream(lexer)
        return PapljAntlrParser(tokens)
    }
}