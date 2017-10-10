package com.virtlink.editorservices.lsp

import org.eclipse.lsp4j.Position
import org.eclipse.lsp4j.Range
import java.util.*

/**
 * Represents an in-memory document.
 * The document may or may not exist on disk.
 */
class VirtualDocument {

    /**
     * Creates a singly-linked list of lines, starting from the last line toward the first (line 0).
     *
     * @property index The zero-based index of the line.
     * @property startOffset The zero-based offset of the start of the line.
     * @property endOffset The zero-based offset of the end of the line.
     * @property length The length of the line.
     * @property text The current text of the line.
     * @property previousLine The previous line, or null when this is line 0.
     */
    private data class Line(val index: Int, val startOffset: Int, val text: String, val previousLine: Line?) {

        constructor(text: String, previousLine: Line?)
        : this(if (previousLine != null) previousLine.index + 1 else 0, previousLine?.endOffset ?: 0, text, previousLine)

        val length get() = this.text.length
        val endOffset get() = this.startOffset + this.length
    }

    /**
     * The last line in the document.
     */
    private var lastLine: Line = Line("", null)

//    private data class LineInfo(val offset: Int, val text: String)
//
//    // For every line in the document we maintain:
//    // The line offset from the start of the document
//    // The line text, including the end of line character(s)
//    private val lines = LinkedList<LineInfo>(
//            listOf(LineInfo(0, ""))     // Line 0
//    )

    /**
     * Updates the document to include the specified change.
     *
     * @param offset The zero-based offset of the start of the changed text.
     * @param length The number of changed characters (including newlines), which may be 0 when text was only inserted.
     * @param newText The next text of the specified range, which may be an empty string when text was only removed.
     */
    fun update(offset: Int, length: Int, newText: String) {
        val lastModifiedLine = findLine(this.lastLine, offset + length)!!
        val firstModifiedLine = findLine(lastModifiedLine, offset)!!

        val originalPrefix = firstModifiedLine.text.substring(0, offset - firstModifiedLine.startOffset)
        val originalSuffix = lastModifiedLine.text.substring((offset + length) - lastModifiedLine.startOffset)

        val lastNewLine = constructNewLines(firstModifiedLine, originalPrefix, newText, originalSuffix)

        this.lastLine = reconstructExistingLines(lastModifiedLine, lastNewLine)
    }

    private fun constructNewLines(firstModifiedLine: Line, prefix: String, newText: String, suffix: String): Line {
        var currentLine = firstModifiedLine.previousLine
        var currentNewTextOffset = 0
        var nextNewTextOffset = findNextLineOffset(newText, currentNewTextOffset)
        if (nextNewTextOffset != null) {
            // Construct the first line
            val firstLineText = newText.substring(currentNewTextOffset, nextNewTextOffset)
            currentLine = Line(prefix + firstLineText, currentLine)
            currentNewTextOffset = nextNewTextOffset

            // Construct each additional line
            nextNewTextOffset = findNextLineOffset(newText, currentNewTextOffset)
            while (nextNewTextOffset != null) {
                val lineText = newText.substring(currentNewTextOffset, nextNewTextOffset)
                currentLine = Line(lineText, currentLine)
                currentNewTextOffset = nextNewTextOffset

                nextNewTextOffset = findNextLineOffset(newText, currentNewTextOffset)
            }

            // Construct the last line
            val lastLineText = newText.substring(currentNewTextOffset)
            currentLine = Line(lastLineText + suffix, currentLine)
        } else {
            // Construct the only line

            val lineText = newText.substring(currentNewTextOffset)
            currentLine = Line(prefix + lineText + suffix, currentLine)
        }

        return currentLine
    }

    private fun reconstructExistingLines(lastModifiedLine: Line, newLine: Line): Line {
        // Gather the remaining unchanged lines in a stack
        val lineStack = Stack<Line>()
        var currentLine = this.lastLine
        while (currentLine != lastModifiedLine) {
            lineStack.push(currentLine)
            currentLine = currentLine.previousLine!!
        }

        // Reconstruct the remaining unchanged lines with their new indices and offsets
        var lastLine = newLine
        while (!lineStack.empty()) {
            val line = lineStack.pop()
            lastLine = Line(line.text, lastLine)
        }

        return lastLine
    }


    /**
     * Finds the line that contains the specified offset.
     */
    private fun findLine(start: Line, offset: Int): Line? {
        if (offset < 0 || start.endOffset <= offset)
            return null

        var currentLine = start
        while (currentLine.startOffset > offset) {
            currentLine = currentLine.previousLine!!
        }
        return currentLine
    }

    /**
     * Gets the offset of the specified line:character within the document.
     *
     * @param line The zero-based line number.
     * @param character The zero-based character offset from the start of the line.
     * @return The zero-based offset from the start of the document;
     * or null when the line or character are out of bounds.
     */
    fun getOffset(line: Int, character: Int): Int? {
        
        TODO()
    }

    /**
     * Gets the line:character of the specified offset within the document.
     *
     * @param offset The zero-based offset from the start of the document.
     * @return The zero-based line number and zero-based character offset from the start of the line,
     * or null when the offset is out of bounds.
     */
    fun getLineCharacterOffset(offset: Int): Position? {
        TODO()
    }

    /**
     * Find the offset of the next line in a string. This may be the end of the string
     * if the string ends with a newline.
     *
     * @param text The text to search through.
     * @param startOffset The zero-based offset at which to start searching.
     * @return The zero-based offset of the start of the next line,
     * which may be at the end of the string; or null when no next line was found.
     */
    private fun findNextLineOffset(text: CharSequence, startOffset: Int): Int? {
        if (startOffset >= text.length) {
            // No more characters in the text.
            return null
        }

        val nextOffset = text.indexOfAny(charArrayOf('\r', '\n'), startOffset)
        return if (nextOffset == -1) {
            // Not found.
            null
        } else if (nextOffset == text.length - 1) {
            // Last character of the file
            nextOffset + 1
        } else if (text[nextOffset] == '\r' && text[nextOffset + 1] == '\n') {
            // CRLF (Windows newline)
            nextOffset + 2
        } else {
            // LF (Unix newline) or CR (old Mac newline)
            nextOffset + 1
        }
    }

}