package com.virtlink.editorservices.content

import com.virtlink.editorservices.Offset
import com.virtlink.editorservices.Position
import com.virtlink.editorservices.indexAfterNextNewline
import java.io.LineNumberReader

/**
 * Line-based document content.
 */
open class LineContent constructor(
        private val lines: List<Line>)
    : IContent {

    /**
     * A line in the document.
     *
     * @property offset The offset of the line.
     * @property text The text of the line, including the end-of-line characters.
     */
    data class Line(val offset: Offset, val text: String) {
        val length get() = this.text.length

        override fun toString(): String
                = this.text
    }

    override val length: Int = getLength(this.lines)

    /**
     * Initializes a new instance of the [LineContent] class.
     *
     * @param text The full text of the document.
     */
    constructor(text: String) : this(getLines(text))

    companion object {

        /**
         * Empty content.
         */
        val empty = LineContent("")

        /**
         * Gets the length from the specified lines.
         *
         * @param lines The lines.
         * @return The length, in characters.
         */
        private fun getLength(lines: List<Line>)
                = lines.sumBy { it.length }

        /**
         * Gets the lines resulting from the specified text.
         *
         * @param text The text.
         * @return The list of lines.
         */
        private fun getLines(text: String): List<Line> {
            val lines = mutableListOf(Line(Offset(0), ""))
            applyChange(lines, Position(0, 0), Position(0, 0), text)
            return lines
        }

        /**
         * Updates the document to include the specified change.
         *
         * @param lines The mutable list of lines to which to apply the change.
         * @param start The start position of the changed text.
         * @param end The end position of the changed text.
         * @param newText The next text of the specified span, which may be an empty string when text was only removed.
         */
        private fun applyChange(lines: MutableList<Line>, start: Position, end: Position, newText: String) {
            val prefix = if (lines.size > 0) lines[start.line].text.substring(0, start.character) else ""
            val suffix = if (lines.size > 0) lines[end.line].text.substring(end.character) else ""

            // Construct the new lines
            val newLines = mutableListOf<Line>()
            var currentLineOffset = 0
            var nextLineOffset = newText.indexAfterNextNewline(currentLineOffset)
            if (nextLineOffset != null) {
                // Construct the first line
                val newFirstLineText = prefix + newText.substring(currentLineOffset, nextLineOffset)
                newLines.add(Line(Offset(currentLineOffset), newFirstLineText))
                currentLineOffset = nextLineOffset

                // Construct the intermediate lines
                nextLineOffset = newText.indexAfterNextNewline(currentLineOffset)
                while (nextLineOffset != null) {
                    val newLineText = newText.substring(currentLineOffset, nextLineOffset)
                    newLines.add(Line(Offset(currentLineOffset), newLineText))
                    currentLineOffset = nextLineOffset

                    nextLineOffset = newText.indexAfterNextNewline(currentLineOffset)
                }

                // Construct the last line
                val newLastLineText = newText.substring(currentLineOffset) + suffix
                newLines.add(Line(Offset(currentLineOffset), newLastLineText))
            } else {
                // Construct the only line
                newLines.add(Line(Offset(currentLineOffset), prefix + newText + suffix))
            }

            // Replace the changed lines
            val sublist = lines.subList(start.line, end.line + 1)
            sublist.clear()
            sublist.addAll(newLines)
        }
    }

    override val lineCount: Int
        get() = this.lines.size

    override fun createReader(): LineNumberReader
            = TODO()

    override fun getOffset(position: Position): Offset? {
        if (position.line >= this.lines.size)
            return null

        val currentLine = position.line
        val currentOffset = this.lines[position.line].offset
        val currentLength = this.lines[currentLine].length

        // If the character is outside the line
        // or at the end of the line while it's not the last line,
        // then the character is out of bounds.
        if (position.character > currentLength ||
                (currentLine < this.lines.size - 1 &&
                        position.character == currentLength)) {
            return null
        }

        return currentOffset + position.character
    }

    override fun getPosition(offset: Offset): Position? {
        var currentOffset = Offset(0)
        var currentLine = 0
        while ((currentLine < this.lines.size - 1 && currentOffset + this.lines[currentLine].length <= offset)
                || (currentLine == this.lines.size && currentOffset + this.lines[currentLine].length < offset)) {
            currentOffset += this.lines[currentLine].length
            currentLine += 1
        }

        if (currentOffset > offset) return null

        return Position(currentLine, offset - currentOffset)
    }

    override fun withChanges(changes: List<TextChange>): IContent {
        val lines = this.lines.toMutableList()
        for (change in changes.asReversed()) {
            val start = this.getPosition(change.span.start)!!
            val end = this.getPosition(change.span.end)!!
            applyChange(lines, start, end, change.newText)
        }
        return LineContent(lines)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (this.javaClass != other?.javaClass) return false
        other as LineContent
        return this.lines == other.lines
    }

    override fun hashCode(): Int {
        var hash = 17
        hash = hash * 23 + this.lines.hashCode()
        return hash
    }

    override fun toString(): String {
        // TODO: Use reader to get whole text.
        val text = StringBuilder()
        this.lines.forEach {
            text.append(it.text)
        }
        return text.toString()
    }

}