package com.virtlink.editorservices.lsp

import com.virtlink.editorservices.Offset
import com.virtlink.editorservices.Position
import com.virtlink.editorservices.Span
import com.virtlink.editorservices.documents.content.IDocumentContent
import org.eclipse.lsp4j.Range

fun Range.toSpan(content: IDocumentContent): Span? {
    val startOffset = this.start.toOffset(content) ?: return null
    val endOffset = this.end.toOffset(content) ?: return null
    return Span(startOffset, endOffset)
}

fun org.eclipse.lsp4j.Position.toOffset(content: IDocumentContent): Offset?
        = content.getOffset(Position(this.line, this.character))
