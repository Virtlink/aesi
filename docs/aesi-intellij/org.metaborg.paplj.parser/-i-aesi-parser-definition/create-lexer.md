---
title: IAesiParserDefinition.createLexer - aesi-intellij
---

[aesi-intellij](../../index.html) / [org.metaborg.paplj.parser](../index.html) / [IAesiParserDefinition](index.html) / [createLexer](.)

# createLexer

`abstract fun createLexer(project: Project, file: PsiFile?): Lexer`

Returns a lexer for lexing the specified file in the specified project.
This lexer does not need to support incremental lexing.

### Parameters

`project` - The project to which the lexer is connected.

`file` - The PSI file being lexed.

**Return**
The lexer.

