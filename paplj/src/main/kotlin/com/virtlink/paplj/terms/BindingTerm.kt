package com.virtlink.paplj.terms

import com.virtlink.terms.*

// GENERATED: This file is generated, do not modify.

/**
 * The BindingTerm term.
 */
@Suppress("UNCHECKED_CAST")
class BindingTerm(
    val name: StringTerm,
    val type: StringTerm,
    val expression: ExprTerm)
    : Term() {

    companion object {

        /**
         * Gets the constructor of this term.
         */
        val constructor = TermConstructorOfT<BindingTerm>("BindingTerm", 3)

        /**
         * Creates a new term from the specified list of child terms.
         *
         * @param children The list of child terms.
         * @return The created term.
         */
        @JvmStatic fun create(children: List<ITerm>): BindingTerm {
            if (children.size != constructor.arity) {
                throw IllegalArgumentException("children must be ${constructor.arity} in length")
            }
            
            val name = children[0] as StringTerm
            val type = children[1] as StringTerm
            val expression = children[2] as ExprTerm
            return BindingTerm(name, type, expression)
        }
    }

    override val constructor: ITermConstructor
        get() = Companion.constructor

    override val children: List<ITerm> by lazy { ChildrenList() }

    private inner class ChildrenList: AbstractList<ITerm>() {

        override val size: Int
            get() = BindingTerm.constructor.arity

        override fun get(index: Int): ITerm
                = when (index) { 
                    0 -> this@BindingTerm.name
                    1 -> this@BindingTerm.type
                    2 -> this@BindingTerm.expression
                    else -> throw IndexOutOfBoundsException()
                }

    }

}
