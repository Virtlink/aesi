package com.virtlink.editorservices.documents

import com.virtlink.editorservices.*
import java.util.concurrent.locks.ReentrantReadWriteLock
import kotlin.concurrent.read
import kotlin.concurrent.write

abstract class VirtualContentSource(override val document: IDocument) : IContentSource {

    private var latestContent: VirtualContent? = null

    private var lock = ReentrantReadWriteLock()

    companion object {
        private val emptyContent = VirtualContent(listOf(Line(Offset(0), "")))
    }

    override fun invalidate() {
        this.lock.write {
            this.latestContent = null
        }
    }

    override fun getLatestContent(): IDocumentContent {
        this.lock.read {
            var content = this.latestContent
            if (content == null) {
                content = readFileContent()
                this.lock.write {
                    this.latestContent = content
                }
            }
            return content
        }
    }

    protected open fun readFileContent(): VirtualContent {
        val text = readText()
        return update(emptyContent, listOf(DocumentChange(Span(Offset(0), Offset(0)), text)))
    }

    /**
     * Reads the file's content.
     */
    abstract protected fun readText(): String

    /**
     * Applies updates to the specified virtual content.
     *
     * All changes must be specified in terms of the document content being changed.
     * Changes must not overlap, and be specified in the order they occur in the document.
     *
     * @param content The document content being changed.
     * @param changes The changes to apply to the content in parallel.
     */
    protected fun update(content: VirtualContent, changes: List<DocumentChange>): VirtualContent {
        val lines = content.lines.toMutableList()
        for (change in changes.asReversed()) {
            val start = content.getPosition(change.span.start)!!
            val end = content.getPosition(change.span.end)!!
            applyChange(lines, start, end, change.newText)
        }
        return VirtualContent(lines)
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

    /**
     * A line in the document.
     *
     * @property offset The offset of the line.
     * @property text The text of the line, including the end-of-line characters.
     */
    protected data class Line(val offset: Offset, val text: String) {
        val length get() = this.text.length

        override fun toString(): String
                = this.text
    }

    /**
     * Virtual content.
     */
    protected class VirtualContent private constructor(
            override val length: Int,
            val lines: List<Line>): IDocumentContent {

        constructor(lines: List<Line>): this(getLength(lines), lines)

        companion object {
            private fun getLength(lines: List<Line>)
                    = lines.sumBy { it.length }
        }

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
    }
}