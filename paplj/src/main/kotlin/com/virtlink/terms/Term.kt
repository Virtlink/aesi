package com.virtlink.terms

import com.virtlink.paplj.utils.HashCodeUtils

/**
 * A term.
 */
abstract class Term: ITerm {

    /** The cached hash code. */
    private val hashCode by lazy { calculateHashCode() }

    abstract override val constructor: ITermConstructor

    abstract override val children: List<ITerm>

    override final fun equals(other: Any?): Boolean
            = equals(other as? ITerm)

    /**
     * Compares this term with the specified term.
     *
     * Two terms are equal when they have the same name,
     * the same arity (number of children), and
     * equal children in the same positions.
     *
     * Annotations and class types do not influence equality.
     */
    open fun equals(other: ITerm?): Boolean {
        // Default implementation:
        @Suppress("SuspiciousEqualsCombination")
        return this === other                       // Cheap referential equality check.
            || (other != null
            && this.constructor == other.constructor
            && this.children == other.children)
    }

    override fun hashCode(): Int
            = this.hashCode

    protected open fun calculateHashCode(): Int {
        // Default implementation:
        return HashCodeUtils.calculateHashCode(this.children)
    }

    /**
     * Returns a human-readable string representation of the term.
     *
     * Only to be used for debugging.
     */
    override fun toString(): String {
        return this.constructor.toString() + if (children.isNotEmpty()) children.joinToString(", ", "(", ")", 16) else ""
    }

}