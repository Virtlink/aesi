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
            return when (ctx.v.type) {
                PapljAntlrLexer.TRUE -> this@AstBuilder.termFactory.createTerm(TrueTerm.constructor)
                PapljAntlrLexer.FALSE -> this@AstBuilder.termFactory.createTerm(FalseTerm.constructor)
                else -> throw RuntimeException("")
            }
        }

        override fun visitNum(ctx: PapljAntlrParser.NumContext): NumTerm {
            val value = visitINT(ctx.INT())
            return this@AstBuilder.termFactory.createTerm(NumTerm.constructor, value)
        }

        override fun visitMember(ctx: PapljAntlrParser.MemberContext): MemberTerm {
            val expr = this@AstBuilder.visitExpr(ctx.expr())
            val name = visitID(ctx.ID())
            return this@AstBuilder.termFactory.createTerm(MemberTerm.constructor, expr, name)
        }

        override fun visitMemberCall(ctx: PapljAntlrParser.MemberCallContext): MemberCallTerm {
            val expr = this@AstBuilder.visitExpr(ctx.expr().first())
            val name = visitID(ctx.ID())
            val arguments = visitListOf(ctx.expr().drop(1), { this@AstBuilder.visitExpr(it) })
            return this@AstBuilder.termFactory.createTerm(MemberCallTerm.constructor, expr, name, arguments)
        }

        override fun visitNegate(ctx: PapljAntlrParser.NegateContext): NegateTerm {
            val expr = this@AstBuilder.visitExpr(ctx.expr())
            return this@AstBuilder.termFactory.createTerm(NegateTerm.constructor, expr)
        }

        override fun visitNot(ctx: PapljAntlrParser.NotContext): NotTerm {
            val expr = this@AstBuilder.visitExpr(ctx.expr())
            return this@AstBuilder.termFactory.createTerm(NotTerm.constructor, expr)
        }

        override fun visitCast(ctx: PapljAntlrParser.CastContext): CastTerm {
            val expr = this@AstBuilder.visitExpr(ctx.expr())
            val type = this@AstBuilder.visitQualifiedName(ctx.qualifiedName())
            return this@AstBuilder.termFactory.createTerm(CastTerm.constructor, expr, type)
        }

        override fun visitMultiplicative(ctx: PapljAntlrParser.MultiplicativeContext): BinOpTerm {
            return createBinOp(ctx.op.type, ctx.expr(0), ctx.expr(1))
        }

        override fun visitAdditive(ctx: PapljAntlrParser.AdditiveContext): BinOpTerm {
            return createBinOp(ctx.op.type, ctx.expr(0), ctx.expr(1))
        }

        override fun visitCompare(ctx: PapljAntlrParser.CompareContext): BinOpTerm {
            return createBinOp(ctx.op.type, ctx.expr(0), ctx.expr(1))
        }

        override fun visitAnd(ctx: PapljAntlrParser.AndContext): BinOpTerm {
            return createBinOp(PapljAntlrLexer.AND, ctx.expr(0), ctx.expr(1))
        }

        override fun visitOr(ctx: PapljAntlrParser.OrContext): BinOpTerm {
            return createBinOp(PapljAntlrLexer.OR, ctx.expr(0), ctx.expr(1))
        }

        private fun createBinOp(op: Int, lhs: PapljAntlrParser.ExprContext, rhs: PapljAntlrParser.ExprContext): BinOpTerm {
            val constructor: ITermConstructor = when (op) {
                PapljAntlrLexer.MUL -> MulTerm.constructor
                PapljAntlrLexer.DIV -> DivTerm.constructor
                PapljAntlrLexer.PLUS -> AddTerm.constructor
                PapljAntlrLexer.MIN -> SubTerm.constructor
                PapljAntlrLexer.EQ -> EqTerm.constructor
                PapljAntlrLexer.NEQ -> NeqTerm.constructor
                PapljAntlrLexer.LT -> LtTerm.constructor
                PapljAntlrLexer.AND -> AndTerm.constructor
                PapljAntlrLexer.OR -> OrTerm.constructor
                else -> throw RuntimeException("")
            }
            val lhsExpr = this@AstBuilder.visitExpr(lhs)
            val rhsExpr = this@AstBuilder.visitExpr(rhs)
            return this@AstBuilder.termFactory.createTerm(constructor, lhsExpr, rhsExpr) as BinOpTerm
        }

        override fun visitAssignment(ctx: PapljAntlrParser.AssignmentContext): AssignTerm {
            val lhsExpr = this@AstBuilder.visitExpr(ctx.expr(0))
            val rhsExpr = this@AstBuilder.visitExpr(ctx.expr(1))
            return this@AstBuilder.termFactory.createTerm(AssignTerm.constructor, lhsExpr, rhsExpr)
        }

        override fun visitLet(ctx: PapljAntlrParser.LetContext): LetTerm {
            val bindings = visitListOf(ctx.binding(), { this@AstBuilder.visitBinding(it) })
            val expr = this@AstBuilder.visitExpr(ctx.expr())
            return this@AstBuilder.termFactory.createTerm(LetTerm.constructor, bindings, expr)
        }

        override fun visitIf(ctx: PapljAntlrParser.IfContext): IfTerm {
            val condition = this@AstBuilder.visitExpr(ctx.expr(0))
            val onTrue = this@AstBuilder.visitExpr(ctx.expr(1))
            val onFalse = this@AstBuilder.visitExpr(ctx.expr(2))
            return this@AstBuilder.termFactory.createTerm(IfTerm.constructor, condition, onTrue, onFalse)
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
     * Visits an integer.
     *
     * @param node The node.
     * @return The integer term.
     */
    private fun visitINT(node: TerminalNode): IntTerm {
        assert(node.symbol.type == PapljAntlrLexer.ID)
        val value = Integer.parseInt(node.symbol.text)
        return this.termFactory.createInt(value)
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