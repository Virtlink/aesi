package com.virtlink.editorservices.documents

import com.virtlink.editorservices.Offset
import com.virtlink.editorservices.Position
import com.virtlink.editorservices.indexAfterNextNewline
import java.io.File
import java.nio.charset.Charset

/**
 * Lightweight document's content.
 *
 * When the truth of the document lies with the local file system,
 * then the document is managed by this class. It doesn't store the
 * document's content, and every call to its methods retrieves the
 * latest version of the document from disk.
 */
class LocalDocumentContent(private val file: File, private val charset: Charset) : IDocumentContent {

    override val length: Int
        get() = this.text.length

    override val text: String
        get() = this.file.readText(charset)

    override val lineCount: Int
        get() {
            // Force reading of file's content and lines.
            val text = this.text
            val lines = getLineOffsets(text)
            return lines.size
        }

    override fun getOffset(position: Position): Offset? {
        // Force reading of file's content and lines.
        val text = this.text
        val lines = getLineOffsets(text)

        if (position.line >= lines.size)
            return null

        val startOffset = lines[position.line]
        val endOffset = if (position.line < lines.size - 1) lines[position.line + 1] else Offset(text.length)
        val length = endOffset - startOffset

        if (position.character > length)
            return null

        return startOffset + position.character
    }

    override fun getPosition(offset: Offset): Position? {
        // Force reading of file's content and lines.
        val text = this.text
        val lines = getLineOffsets(text)

        var currentLine = 0
        while (currentLine < lines.size &&  lines[currentLine] < offset) {
            currentLine += 1
        }
        if (currentLine >= lines.size)
            return null
        return Position(currentLine, offset - lines[currentLine])
    }

    private fun getLineOffsets(text: String): List<Offset> {
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

    override fun toString(): String
            = this.text
}