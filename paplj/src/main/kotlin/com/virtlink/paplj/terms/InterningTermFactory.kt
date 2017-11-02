package com.virtlink.paplj.terms

import com.google.common.collect.Interners

/**
 * Term factory that ensures maximal sharing.
 */
open class InterningTermFactory: DefaultTermFactory() {

    /**
     * The term interner, which ensures that two instances of the same term
     * refer to the same object in memory (reference equality).
     */
    private val interner = Interners.newWeakInterner<Term>()

    override fun createString(value: String): StringTerm
            = getTerm(super.createString(value))

    override fun createInt(value: Int): IntTerm
            = getTerm(super.createInt(value))

    override fun <T: Term> createList(elements: List<T>): ListTerm<T>
            = getTerm(super.createList(elements))

    override fun createTerm(constructor: ITermConstructor, children: List<Term>): Term
            = getTerm(super.createTerm(constructor, children))

    /**
     * Gets the interned term that is equal to the given term;
     * or the given term when the term was not interned yet.
     *
     * This is the core method that ensures maximal sharing.
     *
     * @param term The input term.
     * @return The resulting term.
     */
    private fun <T: Term> getTerm(term: T): T {
        @Suppress("UNCHECKED_CAST")
        return this.interner.intern(term) as T
    }

}