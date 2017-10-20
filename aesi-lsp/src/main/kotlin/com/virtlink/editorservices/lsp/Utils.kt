package com.virtlink.editorservices.lsp

import com.virtlink.editorservices.Offset
import com.virtlink.editorservices.Position
import com.virtlink.editorservices.Span
import com.virtlink.editorservices.content.IContent
import org.eclipse.lsp4j.Range

fun Range.toSpan(content: IContent): Span? {
    val startOffset = this.start.toOffset(content) ?: return null
    val endOffset = this.end.toOffset(content) ?: return null
    return Span(startOffset, endOffset)
}

fun org.eclipse.lsp4j.Position.toOffset(content: IContent): Offset?
        = content.getOffset(Position(this.line, this.character))
