package com.virtlink.editorservices

/**
 * An offset from the start of a document.
 *
 * @property The zero-based offset.
 */
data class Offset(val offset: Int): Comparable<Offset> {

    init {
        if (offset < 0)
            throw IllegalArgumentException("The offset must be greater than or equal to zero.")
    }

    operator fun inc(): Offset
            = this + 1

    operator fun dec(): Offset
            = this - 1

    operator fun plus(value: Int): Offset
            = Offset(this.offset + value)

    operator fun minus(value: Int): Offset
            = Offset(this.offset - value)

    operator fun minus(other: Offset): Int
            = this.offset - other.offset

    // NOTE: To be consistent with Kotlin, rangeTo() accepts
    // the _inclusive_ end of the span. This unfortunately means
    // that `0..0` is not an empty span, but a span of offset 0 up to offset 1.
    // To get the expected behavior, use `0 until 0` instead.
    operator fun rangeTo(other: Offset): Span
            = Span(this, other + 1)

    infix fun until(other: Offset): Span
            = Span(this, other)

    override fun compareTo(other: Offset): Int
            = this.offset.compareTo(other.offset)

    override fun toString(): String
            = this.offset.toString()
}