package com.virtlink.paplj.terms

import com.virtlink.terms.*

// GENERATED: This file is generated, do not modify.

/**
 * The EqTerm term.
 */
@Suppress("UNCHECKED_CAST")
class EqTerm(
    override val lhs: ExprTerm,
    override val rhs: ExprTerm)
    : Term(), BinOpTerm {

    companion object {

        /**
         * Gets the constructor of this term.
         */
        val constructor = TermConstructorOfT("EqTerm", 2, { create(it) })

        /**
         * Creates a new term from the specified list of child terms.
         *
         * @param children The list of child terms.
         * @return The created term.
         */
        @JvmStatic fun create(children: List<ITerm>): EqTerm {
            if (children.size != constructor.arity) {
                throw IllegalArgumentException("children must be ${constructor.arity} in length")
            }
            
            val lhs = children[0] as ExprTerm
            val rhs = children[1] as ExprTerm
            return EqTerm(lhs, rhs)
        }
    }

    override val constructor: ITermConstructor
        get() = Companion.constructor

    override val children: List<ITerm> by lazy { ChildrenList() }

    private inner class ChildrenList: AbstractList<ITerm>() {

        override val size: Int
            get() = EqTerm.constructor.arity

        override fun get(index: Int): ITerm
                = when (index) { 
                    0 -> this@EqTerm.lhs
                    1 -> this@EqTerm.rhs
                    else -> throw IndexOutOfBoundsException()
                }

    }

}
