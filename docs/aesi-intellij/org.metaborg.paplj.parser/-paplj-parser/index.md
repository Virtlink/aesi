---
title: PapljParser - aesi-intellij
---

[aesi-intellij](../../index.html) / [org.metaborg.paplj.parser](../index.html) / [PapljParser](.)

# PapljParser

`open class PapljParser : PsiParser, LightPsiParser`

### Constructors

| [&lt;init&gt;](-init-.html) | `PapljParser()` |

### Properties

| [EXTENDS_SETS_](-e-x-t-e-n-d-s_-s-e-t-s_.html) | `static val EXTENDS_SETS_: `[`Array`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-array/index.html)`<TokenSet>` |

### Functions

| [additive_expr](additive_expr.html) | `open static fun additive_expr(builder_: PsiBuilder, level_: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`): `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [assignment_expr](assignment_expr.html) | `open static fun assignment_expr(builder_: PsiBuilder, level_: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`): `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [binding](binding.html) | `open static fun binding(builder_: PsiBuilder, level_: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`): `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [block_expr](block_expr.html) | `open static fun block_expr(builder_: PsiBuilder, level_: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`): `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [bool_expr](bool_expr.html) | `open static fun bool_expr(builder_: PsiBuilder, level_: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`): `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [cast_expr](cast_expr.html) | `open static fun cast_expr(builder_: PsiBuilder, level_: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`): `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [classDeclaration](class-declaration.html) | `open static fun classDeclaration(builder_: PsiBuilder, level_: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`): `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [comparative_expr](comparative_expr.html) | `open static fun comparative_expr(builder_: PsiBuilder, level_: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`): `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [expr](expr.html) | `open static fun expr(builder_: PsiBuilder, level_: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`): `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [field](field.html) | `open static fun field(builder_: PsiBuilder, level_: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`): `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [if_expr](if_expr.html) | `open static fun if_expr(builder_: PsiBuilder, level_: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`): `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [importStatement](import-statement.html) | `open static fun importStatement(builder_: PsiBuilder, level_: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`): `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [let_expr](let_expr.html) | `open static fun let_expr(builder_: PsiBuilder, level_: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`): `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [logical_and_expr](logical_and_expr.html) | `open static fun logical_and_expr(builder_: PsiBuilder, level_: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`): `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [logical_or_expr](logical_or_expr.html) | `open static fun logical_or_expr(builder_: PsiBuilder, level_: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`): `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [memberDeclaration](member-declaration.html) | `open static fun memberDeclaration(builder_: PsiBuilder, level_: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`): `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [member_expr](member_expr.html) | `open static fun member_expr(builder_: PsiBuilder, level_: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`): `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [method](method.html) | `open static fun method(builder_: PsiBuilder, level_: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`): `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [min_expr](min_expr.html) | `open static fun min_expr(builder_: PsiBuilder, level_: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`): `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [multiplicative_expr](multiplicative_expr.html) | `open static fun multiplicative_expr(builder_: PsiBuilder, level_: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`): `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [new_expr](new_expr.html) | `open static fun new_expr(builder_: PsiBuilder, level_: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`): `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [not_expr](not_expr.html) | `open static fun not_expr(builder_: PsiBuilder, level_: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`): `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [null_expr](null_expr.html) | `open static fun null_expr(builder_: PsiBuilder, level_: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`): `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [num_expr](num_expr.html) | `open static fun num_expr(builder_: PsiBuilder, level_: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`): `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [param](param.html) | `open static fun param(builder_: PsiBuilder, level_: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`): `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [parse](parse.html) | `open fun parse(root_: IElementType, builder_: PsiBuilder): ASTNode` |
| [parseLight](parse-light.html) | `open fun parseLight(root_: IElementType, builder_: PsiBuilder): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [programStatement](program-statement.html) | `open static fun programStatement(builder_: PsiBuilder, level_: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`): `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [runStatement](run-statement.html) | `open static fun runStatement(builder_: PsiBuilder, level_: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`): `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [this_expr](this_expr.html) | `open static fun this_expr(builder_: PsiBuilder, level_: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`): `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [var_expr](var_expr.html) | `open static fun var_expr(builder_: PsiBuilder, level_: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`): `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |

