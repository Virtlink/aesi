package com.virtlink.paplj.terms

import com.google.common.collect.Interners

/**
 * Default implementation of a term factory.
 */
open class DefaultTermFactory : TermFactory() {

    /** The registered term builders. */
    private val termBuilders: HashMap<ITermConstructor, (ITermConstructor, List<ITerm>) -> ITerm> = hashMapOf()

    override fun createString(value: String): StringTerm
            = StringTerm(value)

    override fun createInt(value: Int): IntTerm
            = IntTerm(value)

    override fun <T: ITerm> createList(elements: List<T>): ListTerm<T>
            = ListTerm(elements)

    override fun <T : ITerm> createOption(value: T?): OptionTerm<T>
            = if (value != null) SomeTerm(value) else NoneTerm()

    override fun createTerm(constructor: ITermConstructor, children: List<ITerm>): ITerm {
        if (children.size != constructor.arity) {
            throw IllegalArgumentException("Expected ${constructor.arity} child terms, got ${children.size}.")
        }

        val builder = getBuilder(constructor) ?: { c, cl -> buildTerm(c, cl) }
        return builder(constructor, children)
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
     * Builds a generic term.
     *
     * @param constructor The term constructor.
     * @param children The term's children.
     * @return The created term.
     */
    protected open fun buildTerm(constructor: ITermConstructor, children: List<ITerm>): ITerm {
        assert(constructor.arity == children.size)

        return AnyTerm(constructor, children)
    }

}