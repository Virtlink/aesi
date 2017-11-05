package com.virtlink.paplj.terms

import com.virtlink.terms.*

// GENERATED: This file is generated, do not modify.

/**
 * The MemberTerm term.
 */
@Suppress("UNCHECKED_CAST")
class MemberTerm(
    val expression: ExprTerm,
    val name: StringTerm)
    : Term(), ExprTerm {

    companion object {

        /**
         * Gets the constructor of this term.
         */
        val constructor = TermConstructorOfT<MemberTerm>("MemberTerm", 2)

        /**
         * Creates a new term from the specified list of child terms.
         *
         * @param children The list of child terms.
         * @return The created term.
         */
        @JvmStatic fun create(children: List<ITerm>): MemberTerm {
            if (children.size != constructor.arity) {
                throw IllegalArgumentException("children must be ${constructor.arity} in length")
            }
            
            val expression = children[0] as ExprTerm
            val name = children[1] as StringTerm
            return MemberTerm(expression, name)
        }
    }

    override val constructor: ITermConstructor
        get() = Companion.constructor

    override val children: List<ITerm> by lazy { ChildrenList() }

    private inner class ChildrenList: AbstractList<ITerm>() {

        override val size: Int
            get() = MemberTerm.constructor.arity

        override fun get(index: Int): ITerm
                = when (index) { 
                    0 -> this@MemberTerm.expression
                    1 -> this@MemberTerm.name
                    else -> throw IndexOutOfBoundsException()
                }

    }

}
