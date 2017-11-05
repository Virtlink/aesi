package com.virtlink.paplj.terms

import com.virtlink.terms.*

// GENERATED: This file is generated, do not modify.

/**
 * The NumTerm term.
 */
@Suppress("UNCHECKED_CAST")
class NumTerm(
    val value: IntTerm)
    : Term(), ExprTerm {

    companion object {

        /**
         * Gets the constructor of this term.
         */
        val constructor = TermConstructorOfT("NumTerm", 1, { create(it) })

        /**
         * Creates a new term from the specified list of child terms.
         *
         * @param children The list of child terms.
         * @return The created term.
         */
        @JvmStatic fun create(children: List<ITerm>): NumTerm {
            if (children.size != constructor.arity) {
                throw IllegalArgumentException("children must be ${constructor.arity} in length")
            }
            
            val value = children[0] as IntTerm
            return NumTerm(value)
        }
    }

    override val constructor: ITermConstructor
        get() = Companion.constructor

    override val children: List<ITerm> by lazy { ChildrenList() }

    private inner class ChildrenList: AbstractList<ITerm>() {

        override val size: Int
            get() = NumTerm.constructor.arity

        override fun get(index: Int): ITerm
                = when (index) { 
                    0 -> this@NumTerm.value
                    else -> throw IndexOutOfBoundsException()
                }

    }

}
