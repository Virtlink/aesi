package com.virtlink.paplj.terms

import com.virtlink.terms.*

// GENERATED: This file is generated, do not modify.

/**
 * The NewTerm term.
 */
@Suppress("UNCHECKED_CAST")
class NewTerm(
    val type: StringTerm)
    : Term(), ExprTerm {

    companion object {

        /**
         * Gets the constructor of this term.
         */
        val constructor = TermConstructorOfT("NewTerm", 1, { create(it) })

        /**
         * Creates a new term from the specified list of child terms.
         *
         * @param children The list of child terms.
         * @return The created term.
         */
        @JvmStatic fun create(children: List<ITerm>): NewTerm {
            if (children.size != constructor.arity) {
                throw IllegalArgumentException("children must be ${constructor.arity} in length")
            }
            
            val type = children[0] as StringTerm
            return NewTerm(type)
        }
    }

    override val constructor: ITermConstructor
        get() = Companion.constructor

    override val children: List<ITerm> by lazy { ChildrenList() }

    private inner class ChildrenList: AbstractList<ITerm>() {

        override val size: Int
            get() = NewTerm.constructor.arity

        override fun get(index: Int): ITerm
                = when (index) { 
                    0 -> this@NewTerm.type
                    else -> throw IndexOutOfBoundsException()
                }

    }

}
