package com.virtlink.editorservices.syntaxhighlighting

import com.virtlink.editorservices.Span

data class Token(override val location: Span, override val scope: String) : IToken