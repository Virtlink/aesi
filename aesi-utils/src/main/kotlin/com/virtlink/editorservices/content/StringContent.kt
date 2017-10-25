package com.virtlink.editorservices.content

import com.virtlink.editorservices.Offset
import com.virtlink.editorservices.Position
import com.virtlink.editorservices.indexAfterNextNewline
import java.io.LineNumberReader

/**
 * Simple text content of a document.
 *
 * @param text The full text of the document.
 * @param lines The list of lines in the document.
 */
class StringContent constructor(
        override val text: String,
        val lines: List<Offset>)
    : IContent {

    /**
     * Initializes a new instance of the [StringContent] class.
     *
     * @param text The full text of the document.
     */
    constructor(text: String) : this(text, getLines(text))

    companion object {

        /**
         * Empty content.
         */
        val empty = StringContent("")

        private fun getLines(text: String): List<Offset> {
            val lines = mutableListOf<Offset>()
            var currentLine = 0
            var currentOffset = Offset(0)
            lines.add(currentOffset)

            var nextLine = text.indexAfterNextNewline(currentOffset)
            while (nextLine != null) {
                currentLine += 1
                currentOffset = nextLine
                lines.add(currentOffset)

                nextLine = text.indexAfterNextNewline(currentOffset)
            }

            return lines
        }
    }

    override val length: Int
        get() = this.text.length

    override val lineCount: Int
        get() = this.lines.size

    override fun createReader(): LineNumberReader {
        TODO()
    }

    override fun getOffset(position: Position): Offset? {
        if (position.line >= this.lines.size)
            return null

        val startOffset = this.lines[position.line]
        val endOffset = if (position.line < this.lines.size - 1) this.lines[position.line + 1] else Offset(this.text.length)
        val length = endOffset - startOffset

        if (position.character > length)
            return null

        return startOffset + position.character
    }

    override fun getPosition(offset: Offset): Position? {
        var currentLine = 0
        while (currentLine < this.lines.size && this.lines[currentLine] < offset) {
            currentLine += 1
        }
        if (currentLine >= this.lines.size)
            return null
        return Position(currentLine, offset - this.lines[currentLine])
    }

    override fun withChanges(changes: List<TextChange>): IContent {
        val text = StringBuilder(this.text)
        for (change in changes.asReversed()) {
            text.replace(change.span.start.value, change.span.end.value, change.newText)
        }
        return StringContent(text.toString())
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (this.javaClass != other?.javaClass) return false
        other as StringContent
        return this.text == other.text
            && this.lines == other.lines
    }

    override fun hashCode(): Int {
        var hash = 17
        hash = hash * 23 + this.text.hashCode()
        hash = hash * 23 + this.lines.hashCode()
        return hash
    }

    override fun toString(): String
            = this.text
}