package com.virtlink.paplj.terms

import com.virtlink.terms.*

// GENERATED: This file is generated, do not modify.

/**
 * The NegateTerm term.
 */
@Suppress("UNCHECKED_CAST")
class NegateTerm(
    val expression: ExprTerm)
    : Term(), ExprTerm {

    companion object {

        /**
         * Gets the constructor of this term.
         */
        val constructor = TermConstructorOfT<NegateTerm>("NegateTerm", 1)

        /**
         * Creates a new term from the specified list of child terms.
         *
         * @param children The list of child terms.
         * @return The created term.
         */
        @JvmStatic fun create(children: List<ITerm>): NegateTerm {
            if (children.size != constructor.arity) {
                throw IllegalArgumentException("children must be ${constructor.arity} in length")
            }
            
            val expression = children[0] as ExprTerm
            return NegateTerm(expression)
        }
    }

    override val constructor: ITermConstructor
        get() = Companion.constructor

    override val children: List<ITerm> by lazy { ChildrenList() }

    private inner class ChildrenList: AbstractList<ITerm>() {

        override val size: Int
            get() = NegateTerm.constructor.arity

        override fun get(index: Int): ITerm
                = when (index) { 
                    0 -> this@NegateTerm.expression
                    else -> throw IndexOutOfBoundsException()
                }

    }

}
