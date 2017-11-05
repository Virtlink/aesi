package com.virtlink.terms

/**
 * A list term.
 */
class ListTerm<out T: ITerm>(val elements: List<T>): Term() {

    constructor(vararg elements: T): this(elements.asList())

    override val constructor: ITermConstructor
        get() = TermConstructor("_LIST", this.elements.size)

    override val children: List<ITerm>
        get() = this.elements

    override fun equals(other: ITerm?): Boolean {
        @Suppress("SuspiciousEqualsCombination")
        return this === other                       // Cheap referential equality check.
            || (other != null
            && other is ListTerm<*>
            && this.hashCode() == other.hashCode()  // Usually a cheap inequality check.
            && this.elements == other.elements)
    }

    override fun calculateHashCode(): Int {
        return this.elements.hashCode()
    }

    override fun toString(): String {
        return "[" + this.elements.joinToString(", ") + "]"
    }

}