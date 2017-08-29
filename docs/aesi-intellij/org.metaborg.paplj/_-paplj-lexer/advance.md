---
title: _PapljLexer.advance - aesi-intellij
---

[aesi-intellij](../../index.html) / [org.metaborg.paplj](../index.html) / [_PapljLexer](index.html) / [advance](.)

# advance

`open fun advance(): IElementType`

Resumes scanning until the next regular expression is matched, the end of input is encountered or an I/O-Error occurs.

### Exceptions

`java.io.IOException` - if any I/O-Error occurs

**Return**
the next token

