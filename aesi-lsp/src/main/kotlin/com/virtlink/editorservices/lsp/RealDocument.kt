package com.virtlink.editorservices.lsp

import com.virtlink.editorservices.IDocument
import org.eclipse.lsp4j.Position
import java.io.File
import java.net.URI
import java.util.concurrent.locks.ReentrantReadWriteLock
import kotlin.concurrent.read
import kotlin.concurrent.write

/**
 * Represents a document whose truth is with the file system.
 */
class RealDocument(val uri: URI): ILspDocument {

    private var cachedText: String? = null

    private var lines: List<Int> = emptyList()

    /**
     * Lock.
     */
    private var lock = ReentrantReadWriteLock()

    override val text: String
        get() {
            this.lock.read {
                if (this.cachedText == null) {
                    refresh()
                }
                return this.cachedText!!
            }
        }

    override val length: Int
        get() = this.text.length

    /**
     * Invalidates the in-memory representation of the document.
     */
    fun invalidate() {
        this.lock.write {
            this.cachedText = null
        }
    }

    /**
     * Reads the latest version of the document from disk.
     */
    fun refresh() {
        this.lock.write {
            this.cachedText = DocumentManager.readTextFromDisk(this.uri)
            this.lines = getLineOffsets()
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
    override fun getOffset(line: Int, character: Int): Int? {
        if (line < 0)
            throw IllegalArgumentException("Line must be greater than or equal to zero.")
        if (character < 0)
            throw IllegalArgumentException("Character must be greater than or equal to zero.")

        this.lock.read {
            // Force reading of file's content and lines.
            val text = this.text

            if (line >= this.lines.size)
                return null

            val startOffset = this.lines[line]
            val endOffset = if (line < this.lines.size - 1) this.lines[line + 1] else text.length
            val length = endOffset - startOffset

            if (character > length)
                return null

            return startOffset + character
        }
    }

    /**
     * Gets the line:character of the specified offset within the document.
     *
     * @param offset The zero-based offset from the start of the document.
     * @return The zero-based line number and zero-based character offset from the start of the line,
     * or null when the offset is out of bounds.
     */
    override fun getLineCharacterOffset(offset: Int): Position? {
        if (offset < 0)
            throw IllegalArgumentException("Offset must be greater than or equal to zero.")

        this.lock.read {
            // Force reading of file's content and lines.
            val text = this.text

            var currentLine = 0
            while (currentLine < this.lines.size &&  this.lines[currentLine] < offset) {
                currentLine += 1
            }
            if (currentLine >= this.lines.size)
                return null
            return Position(currentLine, offset - this.lines[currentLine])
        }
    }

    private fun getLineOffsets(): List<Int> {
        this.lock.read {
            // Force reading the file's content.
            val text = this.text

            val lines = mutableListOf<Int>()
            var currentLine = 0
            var currentOffset = 0
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
}
