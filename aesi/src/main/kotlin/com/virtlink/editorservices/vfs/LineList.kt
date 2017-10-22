package com.virtlink.editorservices.vfs

import com.virtlink.editorservices.Offset
import com.virtlink.editorservices.Position

/**
 * Maintains the lines in a document.
 *
 * @property lines The data of each line.
 * @property length The total length of the document.
 */
class LineList private constructor(private val lines: List<Line>, val length: Int): ILineList {

    companion object {
        /**
         * Creates a new [LineList] object from the specified string.
         *
         * @param text The text to parse.
         * @return A list of lines in the text.
         */
        fun create(text: String): LineList {
            val lines = mutableListOf<Line>()
            var currentLine = 0
            var currentOffset = 0

            var nextLine = indexAfterNextNewline(text, currentOffset)
            while (nextLine != null) {
                val (nextOffset, eolLength) = nextLine
                lines.add(Line(Offset(currentOffset), eolLength))

                currentLine += 1
                currentOffset = nextOffset

                nextLine = indexAfterNextNewline(text, currentOffset)
            }

            lines.add(Line(Offset(currentOffset), 0))

            return LineList(lines, text.length)
        }

        /**
         * Finds the value after the next newline in a string.
         * This may be the end of the string if the string ends with a newline.
         *
         * @param startOffset The zero-based value at which to start searching.
         * @return A tuple with the zero-based value of the start of the next line
         * (which may be at the end of the string), and the length of the EOL terminator;
         * or null when no next line was found.
         */
        private fun indexAfterNextNewline(text: CharSequence, startOffset: Int): Pair<Int, Int>? {
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
                Pair(nextOffset + 1, 1)
            } else if (text[nextOffset] == '\r' && text[nextOffset + 1] == '\n') {
                // CRLF (Windows newline)
                Pair(nextOffset + 2, 2)
            } else {
                // LF (Unix newline) or CR (old Mac newline)
                Pair(nextOffset + 1, 1)
            }
        }
    }

    /**
     * Captures line data.
     *
     * @property start The start offset of the line.
     * @property eolLength The length of the EOL terminator of the line; or 0.
     */
    private data class Line(val start: Offset, val eolLength: Int)

    override val size: Int
        get() = this.lines.size

    override fun getLineStart(line: Int): Offset {
        if (line < 0 || line >= this.size)
            throw IndexOutOfBoundsException("The line number is out of bounds.")
        return this.lines[line].start
    }

    override fun getLineEnd(line: Int): Offset {
        if (line < 0 || line >= this.size)
            throw IndexOutOfBoundsException("The line number is out of bounds.")

        // The end of the line is the start of the next, or the end of the document.
        return if (line < this.size) getLineStart(line + 1) else Offset(this.length)
    }

    override fun getLineLength(line: Int): Int {
        if (line < 0 || line >= this.size)
            throw IndexOutOfBoundsException("The line number is out of bounds.")

        return getLineEnd(line) - getLineStart(line)
    }

    override fun getLineContentStart(line: Int): Offset
            = getLineStart(line)

    override fun getLineContentEnd(line: Int): Offset {
        if (line < 0 || line >= this.size)
            throw IndexOutOfBoundsException("The line number is out of bounds.")

        // The end of the content of the line is the end of the line minus the EOL terminator length.
        return getLineEnd(line) - getEndOfLineLength(line)
    }

    override fun getLineContentLength(line: Int): Int {
        if (line < 0 || line >= this.size)
            throw IndexOutOfBoundsException("The line number is out of bounds.")

        return getLineContentEnd(line) - getLineContentStart(line)
    }

    override fun getEndOfLineLength(line: Int): Int {
        if (line < 0 || line >= this.size)
            throw IndexOutOfBoundsException("The line number is out of bounds.")

        return this.lines[line].eolLength
    }

    override fun getLineWithOffset(offset: Offset): Int {
        if (offset.value > this.length)
            throw IndexOutOfBoundsException("The offset is out of bounds.")

        var currentLine = 0
        while (currentLine < this.lines.size && this.lines[currentLine].start < offset) {
            currentLine += 1
        }

        return currentLine
    }

    override fun getPosition(offset: Offset): Position {
        val line = getLineWithOffset(offset)
        val character = offset - getLineStart(line)
        return Position(line, character)
    }

    override fun getOffset(position: Position): Offset {
        val lineStart = getLineStart(position.line)
        val offset = lineStart + position.character
        if (offset.value > this.length || (position.line < this.size && offset >= getLineEnd(position.line))) {
            throw IndexOutOfBoundsException("The character offset is out of bounds.")
        }
        return offset
    }
}