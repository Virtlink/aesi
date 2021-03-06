package com.virtlink.paplj.terms

import com.virtlink.terms.*

// GENERATED: This file is generated, do not modify.

/**
 * The TypeTerm term.
 */
@Suppress("UNCHECKED_CAST")
class TypeTerm(
    val name: StringTerm,
    val extends: OptionTerm<StringTerm>,
    val members: ListTerm<ClassMemberTerm>)
    : Term() {

    companion object {

        /**
         * Gets the constructor of this term.
         */
        val constructor = TermConstructorOfT("TypeTerm", 3, { create(it) })

        /**
         * Creates a new term from the specified list of child terms.
         *
         * @param children The list of child terms.
         * @return The created term.
         */
        @JvmStatic fun create(children: List<ITerm>): TypeTerm {
            if (children.size != constructor.arity) {
                throw IllegalArgumentException("children must be ${constructor.arity} in length")
            }
            
            val name = children[0] as StringTerm
            val extends = children[1] as OptionTerm<StringTerm>
            val members = children[2] as ListTerm<ClassMemberTerm>
            return TypeTerm(name, extends, members)
        }
    }

    override val constructor: ITermConstructor
        get() = Companion.constructor

    override val children: List<ITerm> by lazy { ChildrenList() }

    private inner class ChildrenList: AbstractList<ITerm>() {

        override val size: Int
            get() = TypeTerm.constructor.arity

        override fun get(index: Int): ITerm
                = when (index) { 
                    0 -> this@TypeTerm.name
                    1 -> this@TypeTerm.extends
                    2 -> this@TypeTerm.members
                    else -> throw IndexOutOfBoundsException()
                }

    }

}
