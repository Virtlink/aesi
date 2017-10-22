package com.virtlink.editorservices

import java.io.Serializable

/**
 * An value from the start of a document.
 *
 * @property The zero-based value.
 */
data class Offset(val value: Int): Comparable<Offset>, Serializable {

    init {
        if (value < 0)
            throw IllegalArgumentException("The value must be greater than or equal to zero.")
    }

    operator fun inc(): Offset
            = this + 1

    operator fun dec(): Offset
            = this - 1

    operator fun plus(value: Int): Offset
            = Offset(this.value + value)

    operator fun minus(value: Int): Offset
            = Offset(this.value - value)

    operator fun minus(other: Offset): Int
            = this.value - other.value

    // NOTE: To be consistent with Kotlin, rangeTo() accepts
    // the _inclusive_ end of the span. This unfortunately means
    // that `0..0` is not an empty span, but a span of value 0 up to value 1.
    // To get the expected behavior, use `0 until 0` instead.
    operator fun rangeTo(other: Offset): Span
            = Span(this, other + 1)

    infix fun until(other: Offset): Span
            = Span(this, other)

    override fun compareTo(other: Offset): Int
            = this.value.compareTo(other.value)

    override fun toString(): String
            = this.value.toString()
}