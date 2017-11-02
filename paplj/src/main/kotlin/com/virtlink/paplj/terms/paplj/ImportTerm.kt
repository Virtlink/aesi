package com.virtlink.paplj.terms.paplj

import com.virtlink.paplj.terms.*

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
        @JvmStatic fun create(children: List<ITerm>): ImportTerm {
            if (children.size != constructor.arity) {
                throw IllegalArgumentException("children must be ${constructor.arity} in length")
            }
            val id = children[0] as StringTerm
            return ImportTerm(id)
        }
    }

    override val constructor: ITermConstructor
        get() = Companion.constructor

    override val children: List<ITerm> by lazy { ChildrenList() }

    private inner class ChildrenList: AbstractList<ITerm>() {

        override val size: Int
            get() = ImportTerm.constructor.arity

        override fun get(index: Int): ITerm
                = when (index) {
                    0 -> this@ImportTerm.id
                    else -> throw IndexOutOfBoundsException()
                }

    }

}