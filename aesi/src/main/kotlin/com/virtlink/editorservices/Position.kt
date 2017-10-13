package com.virtlink.editorservices

/**
 * A line:character position in a document.
 *
 * @property line The zero-based line number.
 * @property character The zero-based character offset from the start of the line.
 */
data class Position(val line: Int, val character: Int): Comparable<Position> {

    init {
        if (line < 0)
            throw IllegalArgumentException("The line number must be greater than or equal to zero.")
        if (character < 0)
            throw IllegalArgumentException("The character offset must be greater than or equal to zero.")
    }

    override fun compareTo(other: Position): Int {
        var comparison = 0
        if (comparison == 0) comparison = this.line.compareTo(other.line)
        if (comparison == 0) comparison = this.character.compareTo(other.character)
        return comparison
    }

    override fun toString(): String
            = "$line:$character"
}