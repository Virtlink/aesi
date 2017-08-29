---
title: _PapljLexer.yypushback - aesi-intellij
---

[aesi-intellij](../../index.html) / [org.metaborg.paplj](../index.html) / [_PapljLexer](index.html) / [yypushback](.)

# yypushback

`open fun yypushback(number: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)

Pushes the specified amount of characters back into the input stream. They will be read again by then next call of the scanning method

### Parameters

`number` - the number of characters to be read again. This number must not be greater than yylength()!