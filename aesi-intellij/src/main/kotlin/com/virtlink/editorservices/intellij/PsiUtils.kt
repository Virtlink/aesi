package com.virtlink.editorservices.intellij

import com.intellij.openapi.util.TextRange
import com.virtlink.editorservices.Span

fun Span.toTextRange(): TextRange = TextRange(this.start.value, this.end.value)