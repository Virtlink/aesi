package com.virtlink.editorservices.syntaxcoloring

import com.virtlink.editorservices.Span

interface IToken {
    val location: Span

    val scope: String
}