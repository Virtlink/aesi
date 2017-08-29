---
title: ISyntaxHighlighter.highlight - aesi-java
---

[aesi-java](../../index.html) / [com.virtlink.editorservices.syntaxhighlighting](../index.html) / [ISyntaxHighlighter](index.html) / [highlight](.)

# highlight

`abstract fun highlight(project: `[`IProject`](../../com.virtlink.editorservices/-i-project.html)`, document: `[`IDocument`](../../com.virtlink.editorservices/-i-document/index.html)`, text: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, span: `[`Span`](../../com.virtlink.editorservices/-span/index.html)`, cancellationToken: `[`ICancellationToken`](../../com.virtlink.editorservices/-i-cancellation-token/index.html)`?): `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`IToken`](../-i-token/index.html)`>`

### Parameters

`project` - The project.

`document` - The document to parse.

`text` - The full document text.

`span` - The area of the document to parse.

`cancellationToken` - The cancellation token; or null.