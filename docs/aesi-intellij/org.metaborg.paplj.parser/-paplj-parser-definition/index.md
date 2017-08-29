---
title: PapljParserDefinition - aesi-intellij
---

[aesi-intellij](../../index.html) / [org.metaborg.paplj.parser](../index.html) / [PapljParserDefinition](.)

# PapljParserDefinition

`class PapljParserDefinition : `[`IAesiParserDefinition`](../-i-aesi-parser-definition/index.html)

### Constructors

| [&lt;init&gt;](-init-.html) | `PapljParserDefinition()` |

### Functions

| [createElement](create-element.html) | `fun createElement(node: ASTNode?): PsiElement` |
| [createFile](create-file.html) | `fun createFile(viewProvider: FileViewProvider?): PsiFile?` |
| [createLexer](create-lexer.html) | `fun createLexer(project: Project): Lexer``fun createLexer(project: Project, file: PsiFile?): Lexer`<br>Returns a lexer for lexing the specified file in the specified project. This lexer does not need to support incremental lexing. |
| [createParser](create-parser.html) | `fun createParser(project: Project?): PsiParser?` |
| [getCommentTokens](get-comment-tokens.html) | `fun getCommentTokens(): TokenSet` |
| [getFileNodeType](get-file-node-type.html) | `fun getFileNodeType(): IFileElementType?` |
| [getStringLiteralElements](get-string-literal-elements.html) | `fun getStringLiteralElements(): TokenSet` |
| [getWhitespaceTokens](get-whitespace-tokens.html) | `fun getWhitespaceTokens(): TokenSet` |
| [spaceExistanceTypeBetweenTokens](space-existance-type-between-tokens.html) | `fun spaceExistanceTypeBetweenTokens(left: ASTNode?, right: ASTNode?): SpaceRequirements?` |

### Companion Object Properties

| [FILE](-f-i-l-e.html) | `val FILE: IFileElementType` |
| [WHITE_SPACES](-w-h-i-t-e_-s-p-a-c-e-s.html) | `val WHITE_SPACES: TokenSet` |

