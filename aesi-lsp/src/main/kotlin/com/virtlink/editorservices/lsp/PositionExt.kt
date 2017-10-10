package com.virtlink.editorservices.lsp

import com.virtlink.editorservices.IDocument
import org.eclipse.lsp4j.Position

/**
 * Converts a line:character position in a document
 * to its corresponding zero-based offset.
 *
 * @param document The document in which to find the offset.
 * @return The zero-based offset, or none if out of range.
 */
fun Position.toOffset(document: IDocument): Int? {
    // TODO: Need some fast way to go to and from line:character and offsets,
    // as this will be a bottleneck method. Maybe track a list of line start offsets
    // for each document? This is the naive implementation:

    val text = document.text
    var currentOffset = 0
    var currentLine = 0

    while (currentLine < this.line) {
        val nextLineOffset = findNextLineOffset(text, currentOffset) ?: break
        currentLine += 1
        currentOffset = nextLineOffset
    }

    if (currentLine != this.line) {
        // Line not found
        return null
    }

    return currentOffset + this.character
}

/**
 * Find the offset of the next line. This may be the end of the string
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
        // LF (Unix newline)
        nextOffset + 1
    }
}