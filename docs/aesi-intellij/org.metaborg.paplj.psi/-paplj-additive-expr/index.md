---
title: PapljAdditiveExpr - aesi-intellij
---

[aesi-intellij](../../index.html) / [org.metaborg.paplj.psi](../index.html) / [PapljAdditiveExpr](.)

# PapljAdditiveExpr

`interface PapljAdditiveExpr : `[`PapljExpr`](../-paplj-expr/index.html)

### Functions

| [getExprList](get-expr-list.html) | `abstract fun getExprList(): `[`MutableList`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-list/index.html)`<`[`PapljExpr`](../-paplj-expr/index.html)`>` |
| [getMinus](get-minus.html) | `abstract fun getMinus(): PsiElement?` |
| [getPlus](get-plus.html) | `abstract fun getPlus(): PsiElement?` |

### Inherited Functions

| [getBraceL](../-paplj-expr/get-brace-l.html) | `abstract fun getBraceL(): PsiElement?` |
| [getBraceR](../-paplj-expr/get-brace-r.html) | `abstract fun getBraceR(): PsiElement?` |
| [getExpr](../-paplj-expr/get-expr.html) | `abstract fun getExpr(): `[`PapljExpr`](../-paplj-expr/index.html)`?` |

### Inheritors

| [PapljAdditiveExprImpl](../../org.metaborg.paplj.psi.impl/-paplj-additive-expr-impl/index.html) | `open class PapljAdditiveExprImpl : `[`PapljExprImpl`](../../org.metaborg.paplj.psi.impl/-paplj-expr-impl/index.html)`, PapljAdditiveExpr` |

