package com.virtlink.paplj.terms.paplj

import com.virtlink.paplj.terms.*

/**
 * The Program term.
 */
class ProgramTerm(val id: StringTerm, val imports: ListTerm<ImportTerm>)
    : Term() {

    companion object {

        /**
         * Gets the constructor of this term.
         */
        val constructor = TermConstructorOfT<ProgramTerm>("Program", 2)

        /**
         * Creates a new term from the specified list of child terms.
         *
         * @param children The list of child terms.
         * @return The created term.
         */
        @JvmStatic fun create(children: List<Term>): ProgramTerm {
            if (children.size != constructor.arity) {
                throw IllegalArgumentException("children must be ${constructor.arity} in length")
            }
            val id = children[0] as StringTerm
            @Suppress("UNCHECKED_CAST")
            val imports = children[1] as ListTerm<ImportTerm>
            return ProgramTerm(id, imports)
        }
    }

    override val constructor: ITermConstructor
        get() = Companion.constructor

    override val children: List<Term> by lazy { ChildrenList() }

    private inner class ChildrenList: AbstractList<Term>() {

        override val size: Int
            get() = ProgramTerm.constructor.arity

        override fun get(index: Int): Term
                = when (index) {
                    0 -> this@ProgramTerm.id
                    1 -> this@ProgramTerm.imports
                    else -> throw IndexOutOfBoundsException()
                }

    }

}