---
title: PapljLetExpr - aesi-intellij
---

[aesi-intellij](../../index.html) / [org.metaborg.paplj.psi](../index.html) / [PapljLetExpr](.)

# PapljLetExpr

`interface PapljLetExpr : `[`PapljExpr`](../-paplj-expr/index.html)

### Functions

| [getBindingList](get-binding-list.html) | `abstract fun getBindingList(): `[`MutableList`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-list/index.html)`<`[`PapljBinding`](../-paplj-binding/index.html)`>` |
| [getExpr](get-expr.html) | `abstract fun getExpr(): `[`PapljExpr`](../-paplj-expr/index.html) |
| [getKIn](get-k-in.html) | `abstract fun getKIn(): PsiElement` |
| [getKLet](get-k-let.html) | `abstract fun getKLet(): PsiElement` |

### Inherited Functions

| [getBraceL](../-paplj-expr/get-brace-l.html) | `abstract fun getBraceL(): PsiElement?` |
| [getBraceR](../-paplj-expr/get-brace-r.html) | `abstract fun getBraceR(): PsiElement?` |

### Inheritors

| [PapljLetExprImpl](../../org.metaborg.paplj.psi.impl/-paplj-let-expr-impl/index.html) | `open class PapljLetExprImpl : `[`PapljExprImpl`](../../org.metaborg.paplj.psi.impl/-paplj-expr-impl/index.html)`, PapljLetExpr` |

