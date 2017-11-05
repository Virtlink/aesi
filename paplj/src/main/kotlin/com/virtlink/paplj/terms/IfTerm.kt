package com.virtlink.paplj.terms

import com.virtlink.terms.*

// GENERATED: This file is generated, do not modify.

/**
 * The IfTerm term.
 */
@Suppress("UNCHECKED_CAST")
class IfTerm(
    val condition: ExprTerm,
    val onTrue: ExprTerm,
    val onFalse: ExprTerm)
    : Term(), ExprTerm {

    companion object {

        /**
         * Gets the constructor of this term.
         */
        val constructor = TermConstructorOfT("IfTerm", 3, { create(it) })

        /**
         * Creates a new term from the specified list of child terms.
         *
         * @param children The list of child terms.
         * @return The created term.
         */
        @JvmStatic fun create(children: List<ITerm>): IfTerm {
            if (children.size != constructor.arity) {
                throw IllegalArgumentException("children must be ${constructor.arity} in length")
            }
            
            val condition = children[0] as ExprTerm
            val onTrue = children[1] as ExprTerm
            val onFalse = children[2] as ExprTerm
            return IfTerm(condition, onTrue, onFalse)
        }
    }

    override val constructor: ITermConstructor
        get() = Companion.constructor

    override val children: List<ITerm> by lazy { ChildrenList() }

    private inner class ChildrenList: AbstractList<ITerm>() {

        override val size: Int
            get() = IfTerm.constructor.arity

        override fun get(index: Int): ITerm
                = when (index) { 
                    0 -> this@IfTerm.condition
                    1 -> this@IfTerm.onTrue
                    2 -> this@IfTerm.onFalse
                    else -> throw IndexOutOfBoundsException()
                }

    }

}
