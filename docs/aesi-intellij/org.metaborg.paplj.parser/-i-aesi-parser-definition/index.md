---
title: IAesiParserDefinition - aesi-intellij
---

[aesi-intellij](../../index.html) / [org.metaborg.paplj.parser](../index.html) / [IAesiParserDefinition](.)

# IAesiParserDefinition

`interface IAesiParserDefinition : ParserDefinition`

### Functions

| [createLexer](create-lexer.html) | `abstract fun createLexer(project: Project, file: PsiFile?): Lexer`<br>Returns a lexer for lexing the specified file in the specified project. This lexer does not need to support incremental lexing. |

### Inheritors

| [PapljParserDefinition](../-paplj-parser-definition/index.html) | `class PapljParserDefinition : IAesiParserDefinition` |

