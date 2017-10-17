package com.virtlink.editorservices.documents

import com.virtlink.editorservices.Offset
import com.virtlink.editorservices.Position
import com.virtlink.editorservices.Span

/**
 * Represents the content of a document.
 */
interface IDocumentContent {

    /**
     * Gets the size of the document, in characters.
     */
    val length: Int

    /**
     * Gets the full text of the document.
     */
    val text: String

    /**
     * Gets the number of lines in the document.
     *
     * This is greater than or equal to 1. If the
     * document ends with a newline, the following
     * (empty) line is treated as a line as well.
     */
    val lineCount: Int

    /**
     * Updates the document to include the specified change.
     *
     * To change the document, it must be opened.
     *
     * @param span The span of changed text.
     * @param newText The next text of the specified span,
     * which may be an empty string when text was only removed.
     */
    fun update(span: Span, newText: String)

    /**
     * Gets the offset of the specified line:character position within the document.
     *
     * @param position The position in the document
     * @return The offset in the document;
     * or null when the position is not in the document.
     */
    fun getOffset(position: Position): Offset?

    /**
     * Gets the line:character position of the specified offset within the document.
     *
     * @param offset The offset in the document.
     * @return The position in the document;
     * or null when the offset is not in the document.
     */
    fun getPosition(offset: Offset): Position?
}