package com.virtlink.editorservices.vfs

import com.virtlink.editorservices.Offset
import com.virtlink.editorservices.Position

/**
 * List of lines in a document.
 */
interface ILineList {

    /**
     * Gets the number of lines in the document.
     *
     * This is always at least 1.
     */
    val size: Int

    /**
     * Gets the offset of the start of the specified line.
     *
     * @param line The zero-based line index.
     * @return The zero-based line start offset.
     *
     * @throws IndexOutOfBoundsException The line number is out of bounds.
     */
    fun getLineStart(line: Int): Offset

    /**
     * Gets the offset of the end of the specified line,
     * _after_ the EOL terminator.
     *
     * @param line The zero-based line index.
     * @return The zero-based line end offset.
     *
     * @throws IndexOutOfBoundsException The line number is out of bounds.
     */
    fun getLineEnd(line: Int): Offset

    /**
     * Gets the length of the specified line,
     * _after_ the EOL terminator.
     *
     * @param line The zero-based line index.
     * @return The line's length.
     *
     * @throws IndexOutOfBoundsException The line number is out of bounds.
     */
    fun getLineLength(line: Int): Int

    /**
     * Gets the offset of the start of the specified line.
     *
     * @param line The zero-based line index.
     * @return The zero-based line start offset.
     *
     * @throws IndexOutOfBoundsException The line number is out of bounds.
     */
    fun getLineContentStart(line: Int): Offset

    /**
     * Gets the offset of the end of the specified line,
     * _before_ the EOL terminator.
     *
     * @param line The zero-based line index.
     * @return The zero-based line end offset.
     *
     * @throws IndexOutOfBoundsException The line number is out of bounds.
     */
    fun getLineContentEnd(line: Int): Offset

    /**
     * Gets the length of the specified line,
     * _before_ the EOL terminator.
     *
     * @param line The zero-based line index.
     * @return The line's length.
     *
     * @throws IndexOutOfBoundsException The line number is out of bounds.
     */
    fun getLineContentLength(line: Int): Int

    /**
     * Gets the length of the line's EOL terminator.
     *
     * @param line The zero-based line index.
     * @return The length of the line's EOL terminator,
     * or 0 when the line is the last line in the document.
     *
     * @throws IndexOutOfBoundsException The line number is out of bounds.
     */
    fun getEndOfLineLength(line: Int): Int

    /**
     * Gets the line number that contains the specified offset.
     *
     * @param offset The offset to look for.
     * @return The zero-based line number of the line that contains the offset.
     *
     * @throws IndexOutOfBoundsException The offset is out of bounds.
     */
    fun getLineWithOffset(offset: Offset): Int

    /**
     * Gets the position of the specified offset in the text.
     *
     * @param offset The offset to look for.
     * @return The line:character position of the offset.
     *
     * @throws IndexOutOfBoundsException The offset is out of bounds.
     */
    fun getPosition(offset: Offset): Position

    /**
     * Gets the offset of the specified position in the text.
     *
     * @param position The position to look for.
     * @return The offset of the position.
     *
     * @throws IndexOutOfBoundsException The line number is out of bounds.
     * @throws IndexOutOfBoundsException The character offset is out of bounds.
     */
    fun getOffset(position: Position): Offset
}