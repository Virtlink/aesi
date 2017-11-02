package com.virtlink.paplj.terms.paplj

import com.virtlink.paplj.terms.*

/**
 * The Type term.
 */
class TypeTerm(
        val id: StringTerm,
        val extends: OptionTerm<StringTerm>)
//        val members: List<ClassMemberTerm>)
    : Term() {

    companion object {

        /**
         * Gets the constructor of this term.
         */
        val constructor = TermConstructorOfT<TypeTerm>("Type", 2)

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
            val id = children[0] as StringTerm
            @Suppress("UNCHECKED_CAST")
            val extends = children[1] as OptionTerm<StringTerm>
            return TypeTerm(id, extends)
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
                    0 -> this@TypeTerm.id
                    1 -> this@TypeTerm.extends
                    else -> throw IndexOutOfBoundsException()
                }

    }

}