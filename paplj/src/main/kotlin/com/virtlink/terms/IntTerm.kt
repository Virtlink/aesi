package com.virtlink.terms

/**
 * An integer term.
 */
class IntTerm(val value: Int): Term() {

    companion object {
        /**
         * Gets the constructor of this term.
         */
        val constructor = TermConstructor("_INT", 0)
    }

    override val constructor: ITermConstructor
        get() = Companion.constructor

    override val children: List<ITerm>
        get() = emptyList()

    override fun equals(other: ITerm?): Boolean
            = equals(other as? IntTerm)

    /**
     * Compares this term with the specified term.
     *
     * Two string terms are equal when they have the same content.
     */
    fun equals(other: IntTerm?): Boolean {
        @Suppress("SuspiciousEqualsCombination")
        return this === other                       // Cheap referential equality check.
            || (other != null
            && this.hashCode() == other.hashCode()  // Usually a cheap inequality check.
            && this.value == other.value)
    }

    override fun calculateHashCode(): Int {
        return this.value.hashCode()
    }

    override fun toString(): String {
        return value.toString()
    }

}