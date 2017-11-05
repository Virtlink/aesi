package com.virtlink.paplj.terms

import com.virtlink.terms.*

// GENERATED: This file is generated, do not modify.

/**
 * The MemberCallTerm term.
 */
@Suppress("UNCHECKED_CAST")
class MemberCallTerm(
    val expression: ExprTerm,
    val name: StringTerm,
    val arguments: ListTerm<ExprTerm>)
    : Term(), ExprTerm {

    companion object {

        /**
         * Gets the constructor of this term.
         */
        val constructor = TermConstructorOfT("MemberCallTerm", 3, { create(it) })

        /**
         * Creates a new term from the specified list of child terms.
         *
         * @param children The list of child terms.
         * @return The created term.
         */
        @JvmStatic fun create(children: List<ITerm>): MemberCallTerm {
            if (children.size != constructor.arity) {
                throw IllegalArgumentException("children must be ${constructor.arity} in length")
            }
            
            val expression = children[0] as ExprTerm
            val name = children[1] as StringTerm
            val arguments = children[2] as ListTerm<ExprTerm>
            return MemberCallTerm(expression, name, arguments)
        }
    }

    override val constructor: ITermConstructor
        get() = Companion.constructor

    override val children: List<ITerm> by lazy { ChildrenList() }

    private inner class ChildrenList: AbstractList<ITerm>() {

        override val size: Int
            get() = MemberCallTerm.constructor.arity

        override fun get(index: Int): ITerm
                = when (index) { 
                    0 -> this@MemberCallTerm.expression
                    1 -> this@MemberCallTerm.name
                    2 -> this@MemberCallTerm.arguments
                    else -> throw IndexOutOfBoundsException()
                }

    }

}
