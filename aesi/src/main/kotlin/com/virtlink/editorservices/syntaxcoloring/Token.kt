package com.virtlink.editorservices.syntaxcoloring

import com.virtlink.editorservices.Span

@Deprecated("Replaced by Token2")
data class Token(override val location: Span, override val scope: String) : IToken