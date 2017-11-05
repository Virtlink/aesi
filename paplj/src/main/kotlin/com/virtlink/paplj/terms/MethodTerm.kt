package com.virtlink.paplj.terms

import com.virtlink.terms.*

// GENERATED: This file is generated, do not modify.

/**
 * The MethodTerm term.
 */
@Suppress("UNCHECKED_CAST")
class MethodTerm(
    override val name: StringTerm,
    override val type: StringTerm,
    val params: ListTerm<ParamTerm>,
    val body: Block2Term)
    : Term(), ClassMemberTerm {

    companion object {

        /**
         * Gets the constructor of this term.
         */
        val constructor = TermConstructorOfT("MethodTerm", 4, { create(it) })

        /**
         * Creates a new term from the specified list of child terms.
         *
         * @param children The list of child terms.
         * @return The created term.
         */
        @JvmStatic fun create(children: List<ITerm>): MethodTerm {
            if (children.size != constructor.arity) {
                throw IllegalArgumentException("children must be ${constructor.arity} in length")
            }
            
            val name = children[0] as StringTerm
            val type = children[1] as StringTerm
            val params = children[2] as ListTerm<ParamTerm>
            val body = children[3] as Block2Term
            return MethodTerm(name, type, params, body)
        }
    }

    override val constructor: ITermConstructor
        get() = Companion.constructor

    override val children: List<ITerm> by lazy { ChildrenList() }

    private inner class ChildrenList: AbstractList<ITerm>() {

        override val size: Int
            get() = MethodTerm.constructor.arity

        override fun get(index: Int): ITerm
                = when (index) { 
                    0 -> this@MethodTerm.name
                    1 -> this@MethodTerm.type
                    2 -> this@MethodTerm.params
                    3 -> this@MethodTerm.body
                    else -> throw IndexOutOfBoundsException()
                }

    }

}
