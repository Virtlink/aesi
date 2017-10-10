package com.virtlink.editorservices.lsp

import com.virtlink.editorservices.IDocument
import org.eclipse.lsp4j.Position
import java.io.File
import java.util.concurrent.locks.ReentrantReadWriteLock
import kotlin.concurrent.read
import kotlin.concurrent.write
import java.net.URI

/**
 * Represents an in-memory document.
 * The document may or may not exist on disk.
 */
class VirtualDocument(val uri: URI): IDocument {

    private data class Line(val text: String) {
        val length get() = this.text.length
    }

    override val text: String
        get() {
            val text = StringBuilder()
            this.lines.forEach {
                text.append(it.text)
            }
            return text.toString()
        }

    /**
     * Gets whether the contents of the document is available.
     */
    val available: Boolean
        get() = this.lines.size != 0

    /**
     * The lines in the document.
     */
    private val lines: MutableList<Line> = mutableListOf(Line(""))

    /**
     * Lock.
     */
    private var lock = ReentrantReadWriteLock()

    /**
     * Updates the document to include the specified change.
     *
     * @param offset The zero-based offset of the start of the changed text.
     * @param length The number of changed characters (including newlines), which may be 0 when text was only inserted.
     * @param newText The next text of the specified range, which may be an empty string when text was only removed.
     */
    fun update(offset: Int, length: Int, newText: String) {
        this.lock.read {
            val start = getLineCharacterOffset(offset)!!
            val end = getLineCharacterOffset(offset + length)!!

            val prefix = if (this.lines.size > 0) this.lines[start.line].text.substring(0, start.character) else ""
            val suffix = if (this.lines.size > 0) this.lines[end.line].text.substring(end.character) else ""

            // Construct the new lines
            val newLines = mutableListOf<Line>()
            var currentLineOffset = 0
            var nextLineOffset = findNextLineOffset(newText, currentLineOffset)
            if (nextLineOffset != null) {
                // Construct the first line
                val newFirstLineText = prefix + newText.substring(currentLineOffset, nextLineOffset)
                newLines.add(Line(newFirstLineText))
                currentLineOffset = nextLineOffset

                // Construct the intermediate lines
                nextLineOffset = findNextLineOffset(newText, currentLineOffset)
                while (nextLineOffset != null) {
                    val newLineText = newText.substring(currentLineOffset, nextLineOffset)
                    newLines.add(Line(newLineText))
                    currentLineOffset = nextLineOffset

                    nextLineOffset = findNextLineOffset(newText, currentLineOffset)
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

    /**
     * Gets the offset of the specified line:character within the document.
     *
     * @param line The zero-based line number.
     * @param character The zero-based character offset from the start of the line.
     * @return The zero-based offset from the start of the document;
     * or null when the line or character are out of bounds.
     */
    fun getOffset(line: Int, character: Int): Int? {
        if (line < 0)
            throw IllegalArgumentException("Line must be greater than or equal to 0.")
        if (character < 0)
            throw IllegalArgumentException("Character must be greater than or equal to 0.")

        this.lock.read {
            if (line >= this.lines.size)
                return null

            var currentOffset = 0
            var currentLine = 0
            while (currentLine < line) {
                currentOffset += this.lines[currentLine].length
                currentLine += 1
            }

            val currentLength = this.lines[currentLine].length
            // If the character is outside the line
            // or at the end of the line while it's not the last line,
            // then the character is out of bounds.
            if (character > currentLength ||
               (currentLine < this.lines.size - 1 &&
               character == currentLength)) {
                return null
            }

            return currentOffset + character
        }
    }

    /**
     * Gets the line:character of the specified offset within the document.
     *
     * @param offset The zero-based offset from the start of the document.
     * @return The zero-based line number and zero-based character offset from the start of the line,
     * or null when the offset is out of bounds.
     */
    fun getLineCharacterOffset(offset: Int): Position? {
        if (offset < 0)
            throw IllegalArgumentException("Offset must be greater than or equal to 0.")

        this.lock.read {
            var currentOffset = 0
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