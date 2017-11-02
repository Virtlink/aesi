package com.virtlink.paplj.terms

/**
 * A string term.
 */
class StringTerm(val value: String): Term() {

    companion object {
        /**
         * Gets the constructor of this term.
         */
        val constructor = TermConstructorOfT<IntTerm>("_STRING", 0)
    }

    override val constructor: ITermConstructor
        get() = StringTerm.constructor

    override val children: List<ITerm>
        get() = emptyList()

    override fun equals(other: ITerm?): Boolean
            = equals(other as? StringTerm)

    /**
     * Compares this term with the specified term.
     *
     * Two string terms are equal when they have the same content.
     */
    fun equals(other: StringTerm?): Boolean {
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
        return "\"$value\""
    }

}