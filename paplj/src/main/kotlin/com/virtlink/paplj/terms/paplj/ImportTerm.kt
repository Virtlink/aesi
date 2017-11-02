package com.virtlink.paplj.terms.paplj

import com.virtlink.paplj.terms.ITermConstructor
import com.virtlink.paplj.terms.StringTerm
import com.virtlink.paplj.terms.Term
import com.virtlink.paplj.terms.TermConstructorOfT

/**
 * The Import term.
 */
class ImportTerm(val id: StringTerm)
    : Term() {

    companion object {

        /**
         * Gets the constructor of this term.
         */
        val constructor = TermConstructorOfT<ImportTerm>("Import", 1)

        /**
         * Creates a new term from the specified list of child terms.
         *
         * @param children The list of child terms.
         * @return The created term.
         */
        @JvmStatic fun create(children: List<Term>): ImportTerm {
            if (children.size != constructor.arity) {
                throw IllegalArgumentException("children must be ${constructor.arity} in length")
            }
            val id = children[0] as StringTerm
            return ImportTerm(id)
        }
    }

    override val constructor: ITermConstructor
        get() = Companion.constructor

    override val children: List<Term> by lazy { ChildrenList() }

    private inner class ChildrenList: AbstractList<Term>() {

        override val size: Int
            get() = ImportTerm.constructor.arity

        override fun get(index: Int): Term
                = when (index) {
                    0 -> this@ImportTerm.id
                    else -> throw IndexOutOfBoundsException()
                }

    }

}