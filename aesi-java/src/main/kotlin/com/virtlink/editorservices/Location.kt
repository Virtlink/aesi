package com.virtlink.editorservices

/**
 * A location in a document.
 *
 * @property line The zero-based line number of the location.
 * @property character The zero-based character offset of the location.
 */
data class Location(val line: Int, val character: Int) : Comparable<Location> {

    override fun compareTo(other: Location): Int {
        var comparison = 0
        if (comparison == 0) comparison = this.line.compareTo(other.line)
        if (comparison == 0) comparison = this.character.compareTo(other.character)
        return comparison
    }

    override fun toString(): String {
        return "(${this.line + 1}:${this.character + 1})"
    }

}