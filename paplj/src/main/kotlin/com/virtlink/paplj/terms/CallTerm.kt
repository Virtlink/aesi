package com.virtlink.paplj.terms

import com.virtlink.terms.*

// GENERATED: This file is generated, do not modify.

/**
 * The CallTerm term.
 */
@Suppress("UNCHECKED_CAST")
class CallTerm(
    val name: StringTerm,
    val arguments: ListTerm<ExprTerm>)
    : Term(), ExprTerm {

    companion object {

        /**
         * Gets the constructor of this term.
         */
        val constructor = TermConstructorOfT<CallTerm>("CallTerm", 2)

        /**
         * Creates a new term from the specified list of child terms.
         *
         * @param children The list of child terms.
         * @return The created term.
         */
        @JvmStatic fun create(children: List<ITerm>): CallTerm {
            if (children.size != constructor.arity) {
                throw IllegalArgumentException("children must be ${constructor.arity} in length")
            }
            
            val name = children[0] as StringTerm
            val arguments = children[1] as ListTerm<ExprTerm>
            return CallTerm(name, arguments)
        }
    }

    override val constructor: ITermConstructor
        get() = Companion.constructor

    override val children: List<ITerm> by lazy { ChildrenList() }

    private inner class ChildrenList: AbstractList<ITerm>() {

        override val size: Int
            get() = CallTerm.constructor.arity

        override fun get(index: Int): ITerm
                = when (index) { 
                    0 -> this@CallTerm.name
                    1 -> this@CallTerm.arguments
                    else -> throw IndexOutOfBoundsException()
                }

    }

}
