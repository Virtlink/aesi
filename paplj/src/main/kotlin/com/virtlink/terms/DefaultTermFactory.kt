package com.virtlink.terms

/**
 * Default implementation of a term factory.
 */
open class DefaultTermFactory : TermFactory() {

    /** The registered term builders. */
    private val termBuilders: HashMap<ITermConstructor, (ITermConstructor, List<ITerm>) -> ITerm> = hashMapOf()

    override fun createString(value: String): StringTerm
            = getTerm(StringTerm(value))

    override fun createInt(value: Int): IntTerm
            = getTerm(IntTerm(value))

    override fun <T: ITerm> createList(elements: List<T>): ListTerm<T>
            = getTerm(ListTerm(elements))

    override fun <T : ITerm> createOption(value: T?): OptionTerm<T>
            = getTerm(if (value != null) SomeTerm(value) else NoneTerm())

    override fun createTerm(constructor: ITermConstructor, children: List<ITerm>): ITerm {
        if (children.size != constructor.arity) {
            throw IllegalArgumentException("Expected ${constructor.arity} child terms, got ${children.size}.")
        }

        val builder = getBuilder(constructor) ?: { c, cl -> c.create(cl) }
        return getTerm(builder(constructor, children))
    }

    override fun registerBuilder(constructor: ITermConstructor, builder: (ITermConstructor, List<ITerm>) -> ITerm) {
        this.termBuilders.put(constructor, builder)
    }

    override fun unregisterBuilder(constructor: ITermConstructor) {
        this.termBuilders.remove(constructor)
    }

    /**
     * Gets the term builder with the specified term constructor.
     *
     * @param constructor The term constructor.
     * @return The term builder; or null when not found.
     */
    protected fun getBuilder(constructor: ITermConstructor): ((ITermConstructor, List<ITerm>) -> ITerm)? {
        return this.termBuilders[constructor]
    }

    /**
     * Gets the actual term given a term.
     *
     * @param term The input term.
     * @return The resulting term.
     */
    protected open fun <T: ITerm> getTerm(term: T): T {
        // Default implementation.
        return term
    }

}