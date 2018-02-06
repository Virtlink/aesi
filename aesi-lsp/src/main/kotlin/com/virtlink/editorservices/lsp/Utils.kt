package com.virtlink.editorservices.lsp

import com.virtlink.editorservices.Offset
import com.virtlink.editorservices.Position
import com.virtlink.editorservices.Span
import com.virtlink.editorservices.resources.IContent
import com.virtlink.editorservices.resources.IResourceManager
import org.eclipse.lsp4j.Range

fun Range.toSpan(resourceManager: IResourceManager, content: IContent): Span? {
    val startOffset = this.start.toOffset(resourceManager, content) ?: return null
    val endOffset = this.end.toOffset(resourceManager, content) ?: return null
    return Span(startOffset, endOffset)
}

fun org.eclipse.lsp4j.Position.toOffset(resourceManager: IResourceManager, content: IContent): Offset?
        = resourceManager.getOffset(content, Position(this.line, this.character))

fun Span.toRange(resourceManager: IResourceManager, content: IContent): Range? {
    val start = this.startOffset.toPosition(resourceManager, content) ?: return null
    val end = this.startOffset.toPosition(resourceManager, content) ?: return null
    return Range(start, end)
}

fun Offset.toPosition(resourceManager: IResourceManager, content: IContent): org.eclipse.lsp4j.Position? {
    val position = resourceManager.getPosition(content, this) ?: return null
    return org.eclipse.lsp4j.Position(position.line, position.character)
}