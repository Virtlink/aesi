package com.virtlink.editorservices.syntaxhighlighting

import com.virtlink.editorservices.Span

interface IToken {
    val location: Span

    val scope: String
}