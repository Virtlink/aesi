package com.virtlink.paplj.terms

import com.virtlink.terms.*

// GENERATED: This file is generated, do not modify.

/**
 * The ProgramTerm term.
 */
@Suppress("UNCHECKED_CAST")
class ProgramTerm(
    val name: StringTerm,
    val imports: ListTerm<ImportTerm>,
    val types: ListTerm<TypeTerm>,
    val runExpr: OptionTerm<ExprTerm>)
    : Term() {

    companion object {

        /**
         * Gets the constructor of this term.
         */
        val constructor = TermConstructorOfT<ProgramTerm>("ProgramTerm", 4)

        /**
         * Creates a new term from the specified list of child terms.
         *
         * @param children The list of child terms.
         * @return The created term.
         */
        @JvmStatic fun create(children: List<ITerm>): ProgramTerm {
            if (children.size != constructor.arity) {
                throw IllegalArgumentException("children must be ${constructor.arity} in length")
            }
            
            val name = children[0] as StringTerm
            val imports = children[1] as ListTerm<ImportTerm>
            val types = children[2] as ListTerm<TypeTerm>
            val runExpr = children[3] as OptionTerm<ExprTerm>
            return ProgramTerm(name, imports, types, runExpr)
        }
    }

    override val constructor: ITermConstructor
        get() = Companion.constructor

    override val children: List<ITerm> by lazy { ChildrenList() }

    private inner class ChildrenList: AbstractList<ITerm>() {

        override val size: Int
            get() = ProgramTerm.constructor.arity

        override fun get(index: Int): ITerm
                = when (index) { 
                    0 -> this@ProgramTerm.name
                    1 -> this@ProgramTerm.imports
                    2 -> this@ProgramTerm.types
                    3 -> this@ProgramTerm.runExpr
                    else -> throw IndexOutOfBoundsException()
                }

    }

}
