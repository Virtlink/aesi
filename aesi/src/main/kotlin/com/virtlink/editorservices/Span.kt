package com.virtlink.editorservices

/**
 * A document span.
 *
 * @property start The start offset of the span.
 * @property end The end offset of the span.
 */
data class Span(val startOffset: Int, val endOffset: Int) {

    init {
        if (endOffset < startOffset)
            throw IllegalArgumentException("The start offset must be at or before the end offset.")
    }

    /**
     * Gets whether the span is empty.
     */
    val isEmpty: Boolean
        get() = this.startOffset == this.endOffset

    override fun toString(): String {
        return "(${this.startOffset}-${this.endOffset})"
    }

}