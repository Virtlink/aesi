package com.virtlink.paplj.terms

/**
 * Describes a term constructor.
 */
data class TermConstructorOfT<T: Term>(override val name: String, override val arity: Int): ITermConstructor {

    override fun toString(): String
            = "$name`$arity"

}