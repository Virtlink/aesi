package com.virtlink.paplj.terms

import com.virtlink.terms.*

// GENERATED: This file is generated, do not modify.

/**
 * The NullTerm term.
 */
@Suppress("UNCHECKED_CAST")
class NullTerm(
    val type: OptionTerm<StringTerm>)
    : Term(), ExprTerm {

    companion object {

        /**
         * Gets the constructor of this term.
         */
        val constructor = TermConstructorOfT<NullTerm>("NullTerm", 1)

        /**
         * Creates a new term from the specified list of child terms.
         *
         * @param children The list of child terms.
         * @return The created term.
         */
        @JvmStatic fun create(children: List<ITerm>): NullTerm {
            if (children.size != constructor.arity) {
                throw IllegalArgumentException("children must be ${constructor.arity} in length")
            }
            
            val type = children[0] as OptionTerm<StringTerm>
            return NullTerm(type)
        }
    }

    override val constructor: ITermConstructor
        get() = Companion.constructor

    override val children: List<ITerm> by lazy { ChildrenList() }

    private inner class ChildrenList: AbstractList<ITerm>() {

        override val size: Int
            get() = NullTerm.constructor.arity

        override fun get(index: Int): ITerm
                = when (index) { 
                    0 -> this@NullTerm.type
                    else -> throw IndexOutOfBoundsException()
                }

    }

}
