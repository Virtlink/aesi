package com.virtlink.paplj.terms

import com.virtlink.terms.*

// GENERATED: This file is generated, do not modify.

/**
 * The LtTerm term.
 */
@Suppress("UNCHECKED_CAST")
class LtTerm(
    override val lhs: ExprTerm,
    override val rhs: ExprTerm)
    : Term(), BinOpTerm {

    companion object {

        /**
         * Gets the constructor of this term.
         */
        val constructor = TermConstructorOfT("LtTerm", 2, { create(it) })

        /**
         * Creates a new term from the specified list of child terms.
         *
         * @param children The list of child terms.
         * @return The created term.
         */
        @JvmStatic fun create(children: List<ITerm>): LtTerm {
            if (children.size != constructor.arity) {
                throw IllegalArgumentException("children must be ${constructor.arity} in length")
            }
            
            val lhs = children[0] as ExprTerm
            val rhs = children[1] as ExprTerm
            return LtTerm(lhs, rhs)
        }
    }

    override val constructor: ITermConstructor
        get() = Companion.constructor

    override val children: List<ITerm> by lazy { ChildrenList() }

    private inner class ChildrenList: AbstractList<ITerm>() {

        override val size: Int
            get() = LtTerm.constructor.arity

        override fun get(index: Int): ITerm
                = when (index) { 
                    0 -> this@LtTerm.lhs
                    1 -> this@LtTerm.rhs
                    else -> throw IndexOutOfBoundsException()
                }

    }

}
