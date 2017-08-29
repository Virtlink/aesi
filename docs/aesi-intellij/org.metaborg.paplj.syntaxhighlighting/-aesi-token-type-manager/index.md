---
title: AesiTokenTypeManager - aesi-intellij
---

[aesi-intellij](../../index.html) / [org.metaborg.paplj.syntaxhighlighting](../index.html) / [AesiTokenTypeManager](.)

# AesiTokenTypeManager

`class AesiTokenTypeManager`

Tracks token types.

IntelliJ can't handle too many token types. However, we'll still need a different token type for
each different style. This class returns a token type that matches the style, if found; otherwise,
creates a new token type and stores it for re-use.

The token type manager is specific to a single language.

### Constructors

| [&lt;init&gt;](-init-.html) | `AesiTokenTypeManager()`<br>Tracks token types. |

### Properties

| [defaultScope](default-scope.html) | `val defaultScope: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [defaultTokenType](default-token-type.html) | `val defaultTokenType: `[`AesiTokenType`](../-aesi-token-type/index.html) |

### Functions

| [getTokenType](get-token-type.html) | `fun getTokenType(scope: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`?): `[`AesiTokenType`](../-aesi-token-type/index.html) |

