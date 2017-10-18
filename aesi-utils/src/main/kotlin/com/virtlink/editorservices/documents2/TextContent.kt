package com.virtlink.editorservices.documents2

import com.virtlink.editorservices.Offset
import com.virtlink.editorservices.Position
import com.virtlink.editorservices.indexAfterNextNewline

/**
 * Simple text content of a document.
 *
 * @param text The full text of the document.
 * @param lines The list of lines in the document.
 */
open class TextContent private constructor(override val text: String, private val lines: List<Offset>): IDocumentContent {

    /**
     * Initializes a new instance of the [TextContent] class.
     *
     * @param text The full text of the document.
     */
    constructor(text: String) : this(text, getLines(text))

    companion object {
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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (this.javaClass != other?.javaClass) return false
        other as TextContent
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