package com.virtlink.editorservices

import java.io.Serializable

/**
 * A range in a document.
 *
 * @property start The inclusive start value of the span.
 * @property end The exclusive end value of the span.
 */
data class Span(override val start: Offset, val end: Offset): ClosedRange<Offset>, Iterable<Offset>, Serializable {

    init {
        if (end < start)
            throw IllegalArgumentException("The end value must be at or after the start value.")
    }

    override val endInclusive: Offset
        get() = this.end - 1

    /**
     * Gets the length of the span.
     */
    val length get() = this.end - this.start

    override fun isEmpty(): Boolean
        = this.start == this.end

    override fun iterator(): Iterator<Offset>
        = object : Iterator<Offset> {

            var current = start

            override fun hasNext(): Boolean = current < end

            override fun next(): Offset {
                val next = this.current
                current += 1
                return next
            }
        }

    override fun toString(): String
        = "(${this.start}-${this.end})"

}