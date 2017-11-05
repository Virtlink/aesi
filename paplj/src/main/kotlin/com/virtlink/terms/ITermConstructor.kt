package com.virtlink.terms

/**
 * Describes a term constructor.
 */
interface ITermConstructor {
    val name: String
    val arity: Int

    fun create(children: List<ITerm>): ITerm {
        return AnyTerm(this, children)
    }
}