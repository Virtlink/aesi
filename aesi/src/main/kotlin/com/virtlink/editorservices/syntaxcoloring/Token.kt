package com.virtlink.editorservices.syntaxcoloring

import com.virtlink.editorservices.Span

data class Token(override val location: Span, override val scope: String) : IToken