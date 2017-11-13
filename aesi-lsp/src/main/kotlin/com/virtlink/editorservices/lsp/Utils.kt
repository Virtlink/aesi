package com.virtlink.editorservices.lsp

import com.virtlink.editorservices.Offset
import com.virtlink.editorservices.Position
import com.virtlink.editorservices.Span
import com.virtlink.editorservices.resources.IContent
import org.eclipse.lsp4j.Range

fun Range.toSpan(content: IContent): Span? {
    val startOffset = this.start.toOffset(content) ?: return null
    val endOffset = this.end.toOffset(content) ?: return null
    return Span(startOffset, endOffset)
}

fun org.eclipse.lsp4j.Position.toOffset(content: IContent): Offset?
        = content.getOffset(Position(this.line, this.character))

fun Span.toRange(content: IContent): Range? {
    val start = this.startOffset.toPosition(content) ?: return null
    val end = this.startOffset.toPosition(content) ?: return null
    return Range(start, end)
}

fun Offset.toPosition(content: IContent): org.eclipse.lsp4j.Position? {
    val position = content.getPosition(this) ?: return null
    return org.eclipse.lsp4j.Position(position.line, position.character)
}