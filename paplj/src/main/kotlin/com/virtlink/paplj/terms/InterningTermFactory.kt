package com.virtlink.paplj.terms

import com.google.common.collect.Interners

/**
 * Term factory that ensures maximal sharing.
 *
 * @property innerTermFactory The term factory to wrap.
 */
class InterningTermFactory(private val innerTermFactory: TermFactory): TermFactory() {

    /**
     * Initializes this factory with the default term factory.
     */
    constructor() : this(DefaultTermFactory())

    /**
     * The term interner, which ensures that two instances of the same term
     * refer to the same object in memory (reference equality).
     */
    private val interner = Interners.newWeakInterner<ITerm>()

    override fun createString(value: String): StringTerm
            = getTerm(this.innerTermFactory.createString(value))

    override fun createInt(value: Int): IntTerm
            = getTerm(this.innerTermFactory.createInt(value))

    override fun <T: ITerm> createList(elements: List<T>): ListTerm<T>
            = getTerm(this.innerTermFactory.createList(elements))

    override fun <T : ITerm> createOption(value: T?): OptionTerm<T>
            = getTerm(this.innerTermFactory.createOption(value))

    override fun createTerm(constructor: ITermConstructor, children: List<ITerm>): ITerm
            = getTerm(this.innerTermFactory.createTerm(constructor, children))

    override fun registerBuilder(constructor: ITermConstructor, builder: (ITermConstructor, List<ITerm>) -> ITerm)
            = this.innerTermFactory.registerBuilder(constructor, builder)

    override fun unregisterBuilder(constructor: ITermConstructor)
            = this.innerTermFactory.unregisterBuilder(constructor)

    /**
     * Gets the interned term that is equal to the given term;
     * or the given term when the term was not interned yet.
     *
     * This is the core method that ensures maximal sharing.
     *
     * @param term The input term.
     * @return The resulting term.
     */
    private fun <T: ITerm> getTerm(term: T): T {
        @Suppress("UNCHECKED_CAST")
        return this.interner.intern(term) as T
    }


}