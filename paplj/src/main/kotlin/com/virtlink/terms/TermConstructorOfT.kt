package com.virtlink.terms

/**
 * Describes a term constructor.
 */
data class TermConstructorOfT<T: ITerm>(override val name: String, override val arity: Int): ITermConstructor {

    override fun toString(): String
            = "$name`$arity"

}