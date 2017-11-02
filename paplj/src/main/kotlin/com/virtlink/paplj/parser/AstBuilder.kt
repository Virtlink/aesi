package com.virtlink.paplj.parser

import com.google.inject.Inject
import com.virtlink.paplj.syntax.PapljAntlrParser
import com.virtlink.paplj.syntax.PapljAntlrParserBaseVisitor
import com.virtlink.paplj.terms.ListTerm
import com.virtlink.paplj.terms.StringTerm
import com.virtlink.paplj.terms.Term
import com.virtlink.paplj.terms.TermFactory
import com.virtlink.paplj.terms.paplj.ImportTerm
import com.virtlink.paplj.terms.paplj.ProgramTerm
import org.antlr.v4.runtime.tree.RuleNode

class AstBuilder @Inject constructor(
        private val termFactory: TermFactory)
    : PapljAntlrParserBaseVisitor<Term>() {

    override fun visitProgram(ctx: PapljAntlrParser.ProgramContext): ProgramTerm {
        val id = visitQualifiedName(ctx.qualifiedName())
        val imports = visitListOf(ctx.imports(), { i -> visitImports(i) })
        return this.termFactory.createTerm(ProgramTerm.constructor, id, imports)
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

    /**
     * Visits a list of nodes and constructs a list of terms.
     *
     * @param ctxs The node contexts.
     * @param visitor The visitor function.
     * @return The list term.
     */
    private fun <TIn: RuleNode, TOut: Term> visitListOf(ctxs: List<TIn>, visitor: (TIn) -> TOut): ListTerm<TOut> {
        return this.termFactory.createList(ctxs.map { ctx -> visitor(ctx) })
    }

}