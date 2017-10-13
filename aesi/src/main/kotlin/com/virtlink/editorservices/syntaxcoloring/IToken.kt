package com.virtlink.editorservices.syntaxcoloring

@Deprecated("Replaced by IToken2")
interface IToken {
    val location: Span

    val scope: String
}