package com.virtlink.paplj.terms

import com.virtlink.terms.*

// GENERATED: This file is generated, do not modify.

/**
 * The LetTerm term.
 */
@Suppress("UNCHECKED_CAST")
class LetTerm(
    val bindings: ListTerm<BindingTerm>,
    val expression: ExprTerm)
    : Term(), ExprTerm {

    companion object {

        /**
         * Gets the constructor of this term.
         */
        val constructor = TermConstructorOfT("LetTerm", 2, { create(it) })

        /**
         * Creates a new term from the specified list of child terms.
         *
         * @param children The list of child terms.
         * @return The created term.
         */
        @JvmStatic fun create(children: List<ITerm>): LetTerm {
            if (children.size != constructor.arity) {
                throw IllegalArgumentException("children must be ${constructor.arity} in length")
            }
            
            val bindings = children[0] as ListTerm<BindingTerm>
            val expression = children[1] as ExprTerm
            return LetTerm(bindings, expression)
        }
    }

    override val constructor: ITermConstructor
        get() = Companion.constructor

    override val children: List<ITerm> by lazy { ChildrenList() }

    private inner class ChildrenList: AbstractList<ITerm>() {

        override val size: Int
            get() = LetTerm.constructor.arity

        override fun get(index: Int): ITerm
                = when (index) { 
                    0 -> this@LetTerm.bindings
                    1 -> this@LetTerm.expression
                    else -> throw IndexOutOfBoundsException()
                }

    }

}
