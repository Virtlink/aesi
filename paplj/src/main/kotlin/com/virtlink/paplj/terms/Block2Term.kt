package com.virtlink.paplj.terms

import com.virtlink.terms.*

// GENERATED: This file is generated, do not modify.

/**
 * The Block2Term term.
 */
@Suppress("UNCHECKED_CAST")
class Block2Term(
    val expressions: ListTerm<ExprTerm>)
    : Term(), ExprTerm {

    companion object {

        /**
         * Gets the constructor of this term.
         */
        val constructor = TermConstructorOfT("Block2Term", 1, { create(it) })

        /**
         * Creates a new term from the specified list of child terms.
         *
         * @param children The list of child terms.
         * @return The created term.
         */
        @JvmStatic fun create(children: List<ITerm>): Block2Term {
            if (children.size != constructor.arity) {
                throw IllegalArgumentException("children must be ${constructor.arity} in length")
            }
            
            val expressions = children[0] as ListTerm<ExprTerm>
            return Block2Term(expressions)
        }
    }

    override val constructor: ITermConstructor
        get() = Companion.constructor

    override val children: List<ITerm> by lazy { ChildrenList() }

    private inner class ChildrenList: AbstractList<ITerm>() {

        override val size: Int
            get() = Block2Term.constructor.arity

        override fun get(index: Int): ITerm
                = when (index) { 
                    0 -> this@Block2Term.expressions
                    else -> throw IndexOutOfBoundsException()
                }

    }

}
