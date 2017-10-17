package com.virtlink.editorservices.syntaxcoloring

import com.virtlink.editorservices.Span

data class Token2(
        override val location: Span,
        override val name: String,
        override val attributes: List<String> = emptyList())
    : IToken2