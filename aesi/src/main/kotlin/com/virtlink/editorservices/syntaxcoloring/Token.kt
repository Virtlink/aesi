package com.virtlink.editorservices.syntaxcoloring

@Deprecated("Replaced by Token2")
data class Token(override val location: Span, override val scope: String) : IToken