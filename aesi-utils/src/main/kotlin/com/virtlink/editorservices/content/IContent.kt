package com.virtlink.editorservices.content

import com.virtlink.editorservices.Offset
import com.virtlink.editorservices.Position
import java.io.LineNumberReader

/**
 * Represents the content of a document.
 *
 * This object is immutable.
 */
interface IContent {

    /**
     * Gets the size of the document, in characters.
     */
    val length: Int

    /**
     * Gets the number of lines in the document.
     *
     * This is greater than or equal to 1. If the
     * document ends with a newline, the following
     * (empty) line is treated as a line as well.
     */
    val lineCount: Int

    /**
     * Gets the text in the document.
     */
    val text: String

    /**
     * Creates a reader for this content.
     *
     * Every call creates a new reader that is positioned at the start of the content.
     */
    fun createReader(): LineNumberReader

    /**
     * Gets the value of the specified line:character position within the document.
     *
     * @param position The position in the document
     * @return The value in the document;
     * or null when the position is not in the document.
     */
    fun getOffset(position: Position): Offset?

    /**
     * Gets the line:character position of the specified value within the document.
     *
     * @param offset The value in the document.
     * @return The position in the document;
     * or null when the value is not in the document.
     */
    fun getPosition(offset: Offset): Position?

    /**
     * Returns a new document content object with the specified changes applied.
     *
     * @param changes The changes to apply, in terms of this content.
     * @return The resulting content.
     */
    fun withChanges(changes: List<TextChange>): IContent

}