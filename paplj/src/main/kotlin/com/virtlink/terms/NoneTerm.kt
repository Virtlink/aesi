package com.virtlink.terms

/**
 * The None term.
 */
class NoneTerm<out T: ITerm>
    : Term(), OptionTerm<T> {

    companion object {

        /**
         * Gets the constructor of this term.
         */
        val constructor = TermConstructorOfT<NoneTerm<*>>("_NONE", 0)

        /**
         * Creates a new term from the specified list of child terms.
         *
         * @param children The list of child terms.
         * @return The created term.
         */
        @JvmStatic fun create(children: List<ITerm>): NoneTerm<*> {
            if (children.size != constructor.arity) {
                throw IllegalArgumentException("children must be ${constructor.arity} in length")
            }
            return NoneTerm<ITerm>()
        }
    }

    override val constructor: ITermConstructor
        get() = Companion.constructor

    override val children: List<ITerm> get() = emptyList()

}