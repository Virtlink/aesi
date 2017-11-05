package com.virtlink.paplj.terms

import com.virtlink.terms.*

// GENERATED: This file is generated, do not modify.

/**
 * The DivTerm term.
 */
@Suppress("UNCHECKED_CAST")
class DivTerm(
    override val lhs: ExprTerm,
    override val rhs: ExprTerm)
    : Term(), BinOpTerm {

    companion object {

        /**
         * Gets the constructor of this term.
         */
        val constructor = TermConstructorOfT("DivTerm", 2, { create(it) })

        /**
         * Creates a new term from the specified list of child terms.
         *
         * @param children The list of child terms.
         * @return The created term.
         */
        @JvmStatic fun create(children: List<ITerm>): DivTerm {
            if (children.size != constructor.arity) {
                throw IllegalArgumentException("children must be ${constructor.arity} in length")
            }
            
            val lhs = children[0] as ExprTerm
            val rhs = children[1] as ExprTerm
            return DivTerm(lhs, rhs)
        }
    }

    override val constructor: ITermConstructor
        get() = Companion.constructor

    override val children: List<ITerm> by lazy { ChildrenList() }

    private inner class ChildrenList: AbstractList<ITerm>() {

        override val size: Int
            get() = DivTerm.constructor.arity

        override fun get(index: Int): ITerm
                = when (index) { 
                    0 -> this@DivTerm.lhs
                    1 -> this@DivTerm.rhs
                    else -> throw IndexOutOfBoundsException()
                }

    }

}
