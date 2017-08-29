---
title: IStructureOutliner - aesi-java
---

[aesi-java](../../index.html) / [com.virtlink.editorservices.structureoutline](../index.html) / [IStructureOutliner](.)

# IStructureOutliner

`interface IStructureOutliner`

### Functions

| [outline](outline.html) | `abstract fun outline(project: `[`IProject`](../../com.virtlink.editorservices/-i-project.html)`, document: `[`IDocument`](../../com.virtlink.editorservices/-i-document/index.html)`, symbol: `[`ISymbol`](../-i-symbol/index.html)`?, cancellationToken: `[`ICancellationToken`](../../com.virtlink.editorservices/-i-cancellation-token/index.html)`?): `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`ISymbol`](../-i-symbol/index.html)`>`<br>Returns the child symbols of the given symbol (if any) or the root of the document. |

