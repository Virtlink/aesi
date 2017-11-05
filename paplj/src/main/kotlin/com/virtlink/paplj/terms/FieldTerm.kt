package com.virtlink.paplj.terms

import com.virtlink.terms.*

// GENERATED: This file is generated, do not modify.

/**
 * The FieldTerm term.
 */
@Suppress("UNCHECKED_CAST")
class FieldTerm(
    override val name: StringTerm,
    override val type: StringTerm)
    : Term(), ClassMemberTerm {

    companion object {

        /**
         * Gets the constructor of this term.
         */
        val constructor = TermConstructorOfT("FieldTerm", 2, { create(it) })

        /**
         * Creates a new term from the specified list of child terms.
         *
         * @param children The list of child terms.
         * @return The created term.
         */
        @JvmStatic fun create(children: List<ITerm>): FieldTerm {
            if (children.size != constructor.arity) {
                throw IllegalArgumentException("children must be ${constructor.arity} in length")
            }
            
            val name = children[0] as StringTerm
            val type = children[1] as StringTerm
            return FieldTerm(name, type)
        }
    }

    override val constructor: ITermConstructor
        get() = Companion.constructor

    override val children: List<ITerm> by lazy { ChildrenList() }

    private inner class ChildrenList: AbstractList<ITerm>() {

        override val size: Int
            get() = FieldTerm.constructor.arity

        override fun get(index: Int): ITerm
                = when (index) { 
                    0 -> this@FieldTerm.name
                    1 -> this@FieldTerm.type
                    else -> throw IndexOutOfBoundsException()
                }

    }

}
