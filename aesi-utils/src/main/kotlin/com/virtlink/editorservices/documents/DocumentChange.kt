package com.virtlink.editorservices.documents

import com.virtlink.editorservices.Span

/**
 * Describes a change to a document.
 *
 * @property span The region of the document to change. This may be an empty span when text is only inserted.
 * @property newText The text to replace the span with. This may be an empty string when text is only removed.
 */
data class DocumentChange(
        val span: Span,
        val newText: String
)