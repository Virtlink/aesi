package com.virtlink.paplj.terms

import com.virtlink.terms.*

// GENERATED: This file is generated, do not modify.

/**
 * The ParamTerm term.
 */
@Suppress("UNCHECKED_CAST")
class ParamTerm(
    val name: StringTerm,
    val type: StringTerm)
    : Term() {

    companion object {

        /**
         * Gets the constructor of this term.
         */
        val constructor = TermConstructorOfT<ParamTerm>("ParamTerm", 2)

        /**
         * Creates a new term from the specified list of child terms.
         *
         * @param children The list of child terms.
         * @return The created term.
         */
        @JvmStatic fun create(children: List<ITerm>): ParamTerm {
            if (children.size != constructor.arity) {
                throw IllegalArgumentException("children must be ${constructor.arity} in length")
            }
            
            val name = children[0] as StringTerm
            val type = children[1] as StringTerm
            return ParamTerm(name, type)
        }
    }

    override val constructor: ITermConstructor
        get() = Companion.constructor

    override val children: List<ITerm> by lazy { ChildrenList() }

    private inner class ChildrenList: AbstractList<ITerm>() {

        override val size: Int
            get() = ParamTerm.constructor.arity

        override fun get(index: Int): ITerm
                = when (index) { 
                    0 -> this@ParamTerm.name
                    1 -> this@ParamTerm.type
                    else -> throw IndexOutOfBoundsException()
                }

    }

}
