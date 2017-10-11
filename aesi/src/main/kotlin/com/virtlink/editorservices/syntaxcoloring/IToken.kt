package com.virtlink.editorservices.syntaxcoloring

import com.virtlink.editorservices.Span

@Deprecated("Replaced by IToken2")
interface IToken {
    val location: Span

    val scope: String
}