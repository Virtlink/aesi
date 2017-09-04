package com.virtlink.editorservices

/**
 * A document span.
 *
 * @property start The start of the span.
 * @property end The end of the span.
 */
data class Span(val start: Location, val end: Location) {

    constructor(startLine: Int, startCharacter: Int, endLine: Int, endCharacter: Int)
            : this(Location(startLine, startCharacter), Location(endLine, endCharacter))

    /**
     * Gets whether the span is empty.
     */
    val isEmpty: Boolean
        get() = start == end

    override fun toString(): String {
        return "(${this.start.line + 1}:${this.start.character + 1}-${this.end.line + 1}:${this.end.character + 1})"
    }

}