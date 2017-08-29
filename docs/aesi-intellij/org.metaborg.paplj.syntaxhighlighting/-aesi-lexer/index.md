---
title: AesiLexer - aesi-intellij
---

[aesi-intellij](../../index.html) / [org.metaborg.paplj.syntaxhighlighting](../index.html) / [AesiLexer](.)

# AesiLexer

`class AesiLexer : LexerBase`

### Constructors

| [&lt;init&gt;](-init-.html) | `AesiLexer(project: IProject, document: IDocument, ideaDocument: Document)` |

### Properties

| [document](document.html) | `val document: IDocument` |
| [ideaDocument](idea-document.html) | `val ideaDocument: Document` |
| [project](project.html) | `val project: IProject` |

### Functions

| [advance](advance.html) | `fun advance(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [convertToTokenType](convert-to-token-type.html) | `fun convertToTokenType(token: IToken): IElementType` |
| [getBufferEnd](get-buffer-end.html) | `fun getBufferEnd(): `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [getBufferSequence](get-buffer-sequence.html) | `fun getBufferSequence(): `[`CharSequence`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-char-sequence/index.html) |
| [getState](get-state.html) | `fun getState(): `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [getTokenEnd](get-token-end.html) | `fun getTokenEnd(): `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [getTokenStart](get-token-start.html) | `fun getTokenStart(): `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [getTokenType](get-token-type.html) | `fun getTokenType(): IElementType?` |
| [start](start.html) | `fun start(buffer: `[`CharSequence`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-char-sequence/index.html)`, startOffset: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`, endOffset: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`, initialState: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [tryGetToken](try-get-token.html) | `fun tryGetToken(index: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`): IToken?` |

