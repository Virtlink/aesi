package com.virtlink.paplj.parser

import com.google.inject.Inject
import com.virtlink.paplj.syntax.PapljAntlrLexer
import com.virtlink.paplj.syntax.PapljAntlrParser
import com.virtlink.paplj.syntax.PapljAntlrParserBaseVisitor
import com.virtlink.paplj.terms.*
import com.virtlink.terms.*
import org.antlr.v4.runtime.tree.RuleNode
import org.antlr.v4.runtime.tree.TerminalNode

class AstBuilder @Inject constructor(
        private val termFactory: TermFactory)
    : PapljAntlrParserBaseVisitor<ITerm>() {

    override fun visitProgram(ctx: PapljAntlrParser.ProgramContext): ProgramTerm {
        val id = visitQualifiedName(ctx.qualifiedName())
        val imports = visitListOf(ctx.imports(), { visitImports(it) })
        val types = visitListOf(ctx.type(), { visitType(it) })
        val runExpr = visitMaybe(ctx.expr(), { visitExpr(it) })
        return this.termFactory.createTerm(ProgramTerm.constructor, id, imports, types, runExpr)
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
        val members = visitListOf(ctx.classMember(), { visitClassMember(it) })
        return this.termFactory.createTerm(TypeTerm.constructor, id, extends, members)
    }

    override fun visitClassMember(ctx: PapljAntlrParser.ClassMemberContext): ClassMemberTerm
    = object: PapljAntlrParserBaseVisitor<ClassMemberTerm>() {
        override fun visitField(ctx: PapljAntlrParser.FieldContext): FieldTerm {
            val type = this@AstBuilder.visitQualifiedName(ctx.qualifiedName())
            val name = visitID(ctx.ID())
            return this@AstBuilder.termFactory.createTerm(FieldTerm.constructor, type, name)
        }

        override fun visitMethod(ctx: PapljAntlrParser.MethodContext): MethodTerm {
            val type = this@AstBuilder.visitQualifiedName(ctx.qualifiedName())
            val name = visitID(ctx.ID())
            val params = visitListOf(ctx.param(), { this@AstBuilder.visitParam(it) })
            val body = this@AstBuilder.visitBlock2(ctx.block2())
            return this@AstBuilder.termFactory.createTerm(MethodTerm.constructor, type, name, params, body)
        }
    }.visit(ctx)

    override fun visitParam(ctx: PapljAntlrParser.ParamContext): ParamTerm {
        val type = this@AstBuilder.visitQualifiedName(ctx.qualifiedName())
        val name = visitID(ctx.ID())
        return this.termFactory.createTerm(ParamTerm.constructor, name, type)
    }

    override fun visitBinding(ctx: PapljAntlrParser.BindingContext): BindingTerm {
        val type = this@AstBuilder.visitQualifiedName(ctx.qualifiedName())
        val name = visitID(ctx.ID())
        val expression = visitExpr(ctx.expr())
        return this.termFactory.createTerm(BindingTerm.constructor, name, type, expression)
    }

    override fun visitBlock2(ctx: PapljAntlrParser.Block2Context): Block2Term {
        val expressions = visitListOf(ctx.expr(), { visitExpr(it) })
        return this.termFactory.createTerm(Block2Term.constructor, expressions)
    }

    private fun visitExpr(ctx: PapljAntlrParser.ExprContext): ExprTerm
    = object: PapljAntlrParserBaseVisitor<ExprTerm>() {

        override fun visitParens(ctx: PapljAntlrParser.ParensContext): ExprTerm {
            return visit(ctx.expr())
        }

        override fun visitBlock(ctx: PapljAntlrParser.BlockContext): Block2Term {
            return this@AstBuilder.visitBlock2(ctx.block2())
        }

        override fun visitVar(ctx: PapljAntlrParser.VarContext): VarTerm {
            val id = visitID(ctx.ID())
            return this@AstBuilder.termFactory.createTerm(VarTerm.constructor, id)
        }

        override fun visitCall(ctx: PapljAntlrParser.CallContext): CallTerm {
            val name = visitID(ctx.ID())
            val arguments = visitListOf(ctx.expr(), { this@AstBuilder.visitExpr(it) })
            return this@AstBuilder.termFactory.createTerm(CallTerm.constructor, name, arguments)
        }

        override fun visitNew(ctx: PapljAntlrParser.NewContext): NewTerm {
            val type = this@AstBuilder.visitQualifiedName(ctx.qualifiedName())
            return this@AstBuilder.termFactory.createTerm(NewTerm.constructor, type)
        }

        override fun visitNull(ctx: PapljAntlrParser.NullContext): NullTerm {
            val type = this@AstBuilder.visitQualifiedName(ctx.qualifiedName())
            return this@AstBuilder.termFactory.createTerm(NullTerm.constructor, type)
        }

        override fun visitThis(ctx: PapljAntlrParser.ThisContext): ThisTerm {
            return this@AstBuilder.termFactory.createTerm(ThisTerm.constructor)
        }

        override fun visitBool(ctx: PapljAntlrParser.BoolContext): BoolTerm {

            return this@AstBuilder.termFactory.createTerm(TrueTerm.constructor)
        }

    }.visit(ctx)

    /**
     * Visits an ID.
     *
     * @param node The node.
     * @return The string term.
     */
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


}