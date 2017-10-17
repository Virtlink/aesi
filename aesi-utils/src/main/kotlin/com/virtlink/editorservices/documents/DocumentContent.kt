package com.virtlink.editorservices.documents

import com.virtlink.editorservices.Offset
import com.virtlink.editorservices.Position
import com.virtlink.editorservices.Span
import com.virtlink.editorservices.indexAfterNextNewline
import java.util.concurrent.locks.ReentrantReadWriteLock
import kotlin.concurrent.read
import kotlin.concurrent.write

/**
 * Maintains a document's content.
 *
 * When the truth of the document is maintained remotely
 * (e.g. by the editor), then the manager would send the
 * changes to the document, which are recorded in this object.
 */
class DocumentContent : IDocumentContent {

    override val length: Int
        get() = this.lines.sumBy { it.length }

    override val text: String
        get() {
            val text = StringBuilder()
            this.lines.forEach {
                text.append(it.text)
            }
            return text.toString()
       }

    override val lineCount: Int
        get() = this.lines.size

    /**
     * A line in the document.
     */
    private data class Line(val text: String) {
        val length get() = this.text.length
    }

    /**
     * Lock.
     */
    private var lock = ReentrantReadWriteLock()

    /**
     * The lines in the document.
     */
    private val lines: MutableList<Line> = mutableListOf(Line(""))

    /**
     * Updates the document to include the specified change.
     *
     * @param span The span of changed text.
     * @param newText The next text of the specified span, which may be an empty string when text was only removed.
     */
    override fun update(span: Span, newText: String) {
        this.lock.read {
            val start = getPosition(span.start)!!
            val end = getPosition(span.end)!!

            val prefix = if (this.lines.size > 0) this.lines[start.line].text.substring(0, start.character) else ""
            val suffix = if (this.lines.size > 0) this.lines[end.line].text.substring(end.character) else ""

            // Construct the new lines
            val newLines = mutableListOf<Line>()
            var currentLineOffset = 0
            var nextLineOffset = newText.indexAfterNextNewline(currentLineOffset)
            if (nextLineOffset != null) {
                // Construct the first line
                val newFirstLineText = prefix + newText.substring(currentLineOffset, nextLineOffset)
                newLines.add(Line(newFirstLineText))
                currentLineOffset = nextLineOffset

                // Construct the intermediate lines
                nextLineOffset = newText.indexAfterNextNewline(currentLineOffset)
                while (nextLineOffset != null) {
                    val newLineText = newText.substring(currentLineOffset, nextLineOffset)
                    newLines.add(Line(newLineText))
                    currentLineOffset = nextLineOffset

                    nextLineOffset = newText.indexAfterNextNewline(currentLineOffset)
                }

                // Construct the last line
                val newLastLineText = newText.substring(currentLineOffset) + suffix
                newLines.add(Line(newLastLineText))
            } else {
                // Construct the only line
                newLines.add(Line(prefix + newText + suffix))
            }

            this.lock.write {
                // Replace the changed lines
                val sublist = this.lines.subList(start.line, end.line + 1)
                sublist.clear()
                sublist.addAll(newLines)

            }
        }
    }

    override fun getOffset(position: Position): Offset? {
        this.lock.read {
            if (position.line >= this.lines.size)
                return null

            var currentOffset = Offset(0)
            var currentLine = 0
            while (currentLine < position.line) {
                currentOffset += this.lines[currentLine].length
                currentLine += 1
            }

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
    }

    override fun getPosition(offset: Offset): Position? {
        this.lock.read {
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
    }

    override fun toString(): String
            = this.text
}