package com.virtlink.terms

/**
 * The Some term.
 */
class SomeTerm<out T: ITerm>(val value: T)
    : Term(), OptionTerm<T> {

    companion object {

        /**
         * Gets the constructor of this term.
         */
        val constructor = TermConstructorOfT<SomeTerm<*>>("SomeTerm", 1, { create(it) })

        /**
         * Creates a new term from the specified list of child terms.
         *
         * @param children The list of child terms.
         * @return The created term.
         */
        @JvmStatic fun create(children: List<ITerm>): SomeTerm<*> {
            if (children.size != constructor.arity) {
                throw IllegalArgumentException("children must be ${constructor.arity} in length")
            }
            val id = children[0]
            return SomeTerm(id)
        }
    }

    override val constructor: ITermConstructor
        get() = Companion.constructor

    override val children: List<ITerm> by lazy { ChildrenList() }

    private inner class ChildrenList: AbstractList<ITerm>() {

        override val size: Int
            get() = SomeTerm.constructor.arity

        override fun get(index: Int): ITerm
                = when (index) {
                    0 -> this@SomeTerm.value
                    else -> throw IndexOutOfBoundsException()
                }

    }

}