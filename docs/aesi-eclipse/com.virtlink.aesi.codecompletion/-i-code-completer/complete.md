---
title: ICodeCompleter.complete - aesi-eclipse
---

[aesi-eclipse](../../index.html) / [com.virtlink.aesi.codecompletion](../index.html) / [ICodeCompleter](index.html) / [complete](.)

# complete

`abstract fun complete(document: IAesiDocument, caretOffset: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`, @Nullable cancellationToken: @Nullable ICancellationToken?): `[`MutableList`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-list/index.html)`<ICompletionProposal>`

Returns completion proposals for the current caret position in the text in a document. The completion proposals must be returned in the order they must be displayed to the user. This function should filter the returned proposals by the prefix that the user has typed. The cancellation token is used to abort a long-running operation when the result is no longer needed. Implementations should listen to the cancellation event and abort, for example by throwing an exception or returning a dummy result. It is allowed to ignore the cancellation event, but this may negatively impact performance.

### Parameters

`document` - The document for which to provide completions.

`caretOffset` - The offset of the caret in the document.

`cancellationToken` - The cancellation token; or `null` when not supported.

**Return**
The list of tokens in the colorized document.

