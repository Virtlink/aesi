package com.virtlink.terms

/**
 * Describes a term constructor.
 */
data class TermConstructorOfT<out T: ITerm>(
        override val name: String,
        override val arity: Int,
        private val builder: (List<ITerm>) -> T): ITermConstructor {

    override fun create(children: List<ITerm>): T
            = this.builder(children)

    override fun toString(): String
            = "$name`$arity"

}