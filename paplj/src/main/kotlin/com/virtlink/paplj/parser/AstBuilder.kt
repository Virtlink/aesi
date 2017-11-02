package com.virtlink.paplj.parser

import com.google.inject.Inject
import com.virtlink.paplj.syntax.PapljAntlrLexer
import com.virtlink.paplj.syntax.PapljAntlrParser
import com.virtlink.paplj.syntax.PapljAntlrParserBaseVisitor
import com.virtlink.paplj.terms.*
import com.virtlink.paplj.terms.paplj.*
import org.antlr.v4.runtime.tree.RuleNode
import org.antlr.v4.runtime.tree.TerminalNode

class AstBuilder @Inject constructor(
        private val termFactory: TermFactory)
    : PapljAntlrParserBaseVisitor<Term>() {

    override fun visitProgram(ctx: PapljAntlrParser.ProgramContext): ProgramTerm {
        val id = visitQualifiedName(ctx.qualifiedName())
        val imports = visitListOf(ctx.imports(), { visitImports(it) })
        val types = visitListOf(ctx.type(), { visitType(it) })
        val expr = visitMaybe(ctx.expr(), { visitExpr(it) })
        return this.termFactory.createTerm(ProgramTerm.constructor, id, imports, types, expr)
    }

    override fun visitQualifiedName(ctx: PapljAntlrParser.QualifiedNameContext): StringTerm {
        return this.termFactory.createString(ctx.ID().joinToString("."))
    }

    override fun visitQualifiedNameWithWildcard(ctx: PapljAntlrParser.QualifiedNameWithWildcardContext): Term {
        // FIXME: This is a bit hacky and brittle.
        val postfix = if (ctx.children.size == 2) ".*" else ""
        return this.termFactory.createString(ctx.qualifiedName().ID().joinToString(".", postfix=postfix))
    }

    override fun visitImports(ctx: PapljAntlrParser.ImportsContext): ImportTerm {
        val id = visitQualifiedNameWithWildcard(ctx.qualifiedNameWithWildcard())
        return this.termFactory.createTerm(ImportTerm.constructor, id)
    }

    override fun visitType(ctx: PapljAntlrParser.TypeContext): TypeTerm {
        val id = visitID(ctx.ID())
        val extends = visitMaybe(ctx.qualifiedName(), { visitQualifiedName(it) })
        return this.termFactory.createTerm(TypeTerm.constructor, id, extends)
    }

    private fun visitExpr(ctx: PapljAntlrParser.ExprContext): ExprTerm {
        return ExprBuilder().visit(ctx)
    }

    private fun visitID(node: TerminalNode): StringTerm {
        assert(node.symbol.type == PapljAntlrLexer.ID)
        return this.termFactory.createString(node.symbol.text)
    }

    /**
     * Visits a list of nodes and constructs a list of terms.
     *
     * @param nodes The nodes.
     * @param transform The transform function.
     * @return The list term.
     */
    private fun <TIn: RuleNode, TOut: ITerm> visitListOf(nodes: List<TIn>, transform: (TIn) -> TOut): ListTerm<TOut> {
        return this.termFactory.createList(nodes.map { transform(it) })
    }

    /**
     * Visits an optional node and constructs an option term.
     *
     * @param node The node; or null.
     * @param transform The transform function.
     * @return The option term.
     */
    private fun <TIn: RuleNode, TOut: ITerm> visitMaybe(node: TIn?, transform: (TIn) -> TOut): OptionTerm<TOut> {
        return this.termFactory.createOption(if (node != null) transform(node) else null)
    }

    private inner class ExprBuilder : PapljAntlrParserBaseVisitor<ExprTerm>() {

        override fun visitParens(ctx: PapljAntlrParser.ParensContext): ExprTerm {
            return visit(ctx.expr())
        }

        override fun visitBlock(ctx: PapljAntlrParser.BlockContext): ExprTerm {
            return visit(ctx.block2())
        }

        override fun visitVar(ctx: PapljAntlrParser.VarContext): ExprTerm {
            val id = visitID(ctx.ID())// this@AstBuilder.termFactory.createString(ctx.ID().symbol.text)
            return this@AstBuilder.termFactory.createTerm(VarTerm.constructor, id)
        }
    }

}