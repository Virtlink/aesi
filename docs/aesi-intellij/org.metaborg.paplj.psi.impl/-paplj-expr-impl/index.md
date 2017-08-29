---
title: PapljExprImpl - aesi-intellij
---

[aesi-intellij](../../index.html) / [org.metaborg.paplj.psi.impl](../index.html) / [PapljExprImpl](.)

# PapljExprImpl

`open class PapljExprImpl : `[`PapljCompositeElementImpl`](../-paplj-composite-element-impl/index.html)`, `[`PapljExpr`](../../org.metaborg.paplj.psi/-paplj-expr/index.html)

### Constructors

| [&lt;init&gt;](-init-.html) | `PapljExprImpl(node: ASTNode)` |

### Functions

| [accept](accept.html) | `open fun accept(visitor: `[`PapljVisitor`](../../org.metaborg.paplj.psi/-paplj-visitor/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>`open fun accept(visitor: PsiElementVisitor): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [getBraceL](get-brace-l.html) | `open fun getBraceL(): PsiElement?` |
| [getBraceR](get-brace-r.html) | `open fun getBraceR(): PsiElement?` |
| [getExpr](get-expr.html) | `open fun getExpr(): `[`PapljExpr`](../../org.metaborg.paplj.psi/-paplj-expr/index.html)`?` |

### Inherited Functions

| [getReference](../-paplj-composite-element-impl/get-reference.html) | `open fun getReference(): PsiReference?` |

### Inheritors

| [PapljAdditiveExprImpl](../-paplj-additive-expr-impl/index.html) | `open class PapljAdditiveExprImpl : PapljExprImpl, `[`PapljAdditiveExpr`](../../org.metaborg.paplj.psi/-paplj-additive-expr/index.html) |
| [PapljAssignmentExprImpl](../-paplj-assignment-expr-impl/index.html) | `open class PapljAssignmentExprImpl : PapljExprImpl, `[`PapljAssignmentExpr`](../../org.metaborg.paplj.psi/-paplj-assignment-expr/index.html) |
| [PapljBlockExprImpl](../-paplj-block-expr-impl/index.html) | `open class PapljBlockExprImpl : PapljExprImpl, `[`PapljBlockExpr`](../../org.metaborg.paplj.psi/-paplj-block-expr/index.html) |
| [PapljBoolExprImpl](../-paplj-bool-expr-impl/index.html) | `open class PapljBoolExprImpl : PapljExprImpl, `[`PapljBoolExpr`](../../org.metaborg.paplj.psi/-paplj-bool-expr/index.html) |
| [PapljCastExprImpl](../-paplj-cast-expr-impl/index.html) | `open class PapljCastExprImpl : PapljExprImpl, `[`PapljCastExpr`](../../org.metaborg.paplj.psi/-paplj-cast-expr/index.html) |
| [PapljComparativeExprImpl](../-paplj-comparative-expr-impl/index.html) | `open class PapljComparativeExprImpl : PapljExprImpl, `[`PapljComparativeExpr`](../../org.metaborg.paplj.psi/-paplj-comparative-expr/index.html) |
| [PapljIfExprImpl](../-paplj-if-expr-impl/index.html) | `open class PapljIfExprImpl : PapljExprImpl, `[`PapljIfExpr`](../../org.metaborg.paplj.psi/-paplj-if-expr/index.html) |
| [PapljLetExprImpl](../-paplj-let-expr-impl/index.html) | `open class PapljLetExprImpl : PapljExprImpl, `[`PapljLetExpr`](../../org.metaborg.paplj.psi/-paplj-let-expr/index.html) |
| [PapljLogicalAndExprImpl](../-paplj-logical-and-expr-impl/index.html) | `open class PapljLogicalAndExprImpl : PapljExprImpl, `[`PapljLogicalAndExpr`](../../org.metaborg.paplj.psi/-paplj-logical-and-expr/index.html) |
| [PapljLogicalOrExprImpl](../-paplj-logical-or-expr-impl/index.html) | `open class PapljLogicalOrExprImpl : PapljExprImpl, `[`PapljLogicalOrExpr`](../../org.metaborg.paplj.psi/-paplj-logical-or-expr/index.html) |
| [PapljMemberExprImpl](../-paplj-member-expr-impl/index.html) | `open class PapljMemberExprImpl : PapljExprImpl, `[`PapljMemberExpr`](../../org.metaborg.paplj.psi/-paplj-member-expr/index.html) |
| [PapljMinExprImpl](../-paplj-min-expr-impl/index.html) | `open class PapljMinExprImpl : PapljExprImpl, `[`PapljMinExpr`](../../org.metaborg.paplj.psi/-paplj-min-expr/index.html) |
| [PapljMultiplicativeExprImpl](../-paplj-multiplicative-expr-impl/index.html) | `open class PapljMultiplicativeExprImpl : PapljExprImpl, `[`PapljMultiplicativeExpr`](../../org.metaborg.paplj.psi/-paplj-multiplicative-expr/index.html) |
| [PapljNewExprImpl](../-paplj-new-expr-impl/index.html) | `open class PapljNewExprImpl : PapljExprImpl, `[`PapljNewExpr`](../../org.metaborg.paplj.psi/-paplj-new-expr/index.html) |
| [PapljNotExprImpl](../-paplj-not-expr-impl/index.html) | `open class PapljNotExprImpl : PapljExprImpl, `[`PapljNotExpr`](../../org.metaborg.paplj.psi/-paplj-not-expr/index.html) |
| [PapljNullExprImpl](../-paplj-null-expr-impl/index.html) | `open class PapljNullExprImpl : PapljExprImpl, `[`PapljNullExpr`](../../org.metaborg.paplj.psi/-paplj-null-expr/index.html) |
| [PapljNumExprImpl](../-paplj-num-expr-impl/index.html) | `open class PapljNumExprImpl : PapljExprImpl, `[`PapljNumExpr`](../../org.metaborg.paplj.psi/-paplj-num-expr/index.html) |
| [PapljThisExprImpl](../-paplj-this-expr-impl/index.html) | `open class PapljThisExprImpl : PapljExprImpl, `[`PapljThisExpr`](../../org.metaborg.paplj.psi/-paplj-this-expr/index.html) |
| [PapljVarExprImpl](../-paplj-var-expr-impl/index.html) | `open class PapljVarExprImpl : PapljExprImpl, `[`PapljVarExpr`](../../org.metaborg.paplj.psi/-paplj-var-expr/index.html) |

