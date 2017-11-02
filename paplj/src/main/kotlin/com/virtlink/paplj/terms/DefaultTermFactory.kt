package com.virtlink.paplj.terms

import com.google.common.collect.Interners

/**
 * Default implementation of a term factory.
 */
open class DefaultTermFactory : TermFactory() {

    override fun createString(value: String): StringTerm
            = StringTerm(value)

    override fun createInt(value: Int): IntTerm
            = IntTerm(value)

    override fun <T: Term> createList(elements: List<T>): ListTerm<T>
            = ListTerm(elements)

    override fun createTerm(constructor: ITermConstructor, children: List<Term>): Term {
        if (children.size != constructor.arity) {
            throw IllegalArgumentException("Expected ${constructor.arity} child terms, got ${children.size}.")
        }

        val builder = getBuilder(constructor) ?: { c, cl -> buildTerm(c, cl) }
        return builder(constructor, children)
    }

    /**
     * Builds a generic term.
     *
     * @param constructor The term constructor.
     * @param children The term's children.
     * @return The created term.
     */
    protected open fun buildTerm(constructor: ITermConstructor, children: List<Term>): Term {
        assert(constructor.arity == children.size)

        return AnyTerm(constructor, children)
    }

}