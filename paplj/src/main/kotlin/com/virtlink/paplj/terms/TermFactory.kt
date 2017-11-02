package com.virtlink.paplj.terms

/**
 * Base class for term factories.
 */
abstract class TermFactory {

    /**
     * Creates a string term with the specified value.
     *
     * @param value The value.
     * @return The created term.
     */
    abstract fun createString(value: String): StringTerm

    /**
     * Creates an integer term with the specified value.
     *
     * @param value The value.
     * @return The created term.
     */
    abstract fun createInt(value: Int): IntTerm

    /**
     * Creates a list with elements of the specified type.
     *
     * @param elements The elements in the list.
     * @return The list.
     */
    abstract fun <T: ITerm> createList(elements: List<T>): ListTerm<T>

    /**
     * Creates an option which is either Some or None.
     *
     * @param value The value in the option; or null.
     * @return The option.
     */
    abstract fun <T: ITerm> createOption(value: T?): OptionTerm<T>

    /**
     * Creates a term with the specified constructor and children.
     *
     * @param constructor The constructor.
     * @param children The children.
     *
     * @return The created term.
     */
    abstract fun createTerm(constructor: ITermConstructor, children: List<ITerm>): ITerm

    /**
     * Creates a term with the specified name and children.
     *
     * @param name The constructor name.
     * @param children The children.
     * @return The created term.
     */
    @Suppress("NOTHING_TO_INLINE")
    inline fun createTerm(name: String, vararg children: ITerm): ITerm
            = createTerm(name, children.asList())

    /**
     * Creates a term with the specified name and children.
     *
     * @param name The constructor name.
     * @param children The children.
     * @return The created term.
     */
    @Suppress("NOTHING_TO_INLINE")
    inline fun createTerm(name: String, children: List<ITerm>): ITerm
            = createTerm(TermConstructor(name, children.size), children)

    /**
     * Creates a term with the specified constructor and children.
     *
     * @param constructor The constructor.
     * @param children The children.
     *
     * @return The created term.
     */
    @Suppress("NOTHING_TO_INLINE")
    inline fun createTerm(constructor: ITermConstructor, vararg children: ITerm): ITerm
            = createTerm(constructor, children.asList())

    /**
     * Creates a term with the specified constructor and children.
     *
     * @param constructor The constructor.
     * @param children The children.
     *
     * @return The created term.
     */
    inline fun <reified T: ITerm> createTerm(constructor: TermConstructorOfT<T>, vararg children: ITerm): T {
        return createTerm(constructor, children.asList())
    }

    /**
     * Creates a term with the specified constructor and children.
     *
     * @param constructor The constructor.
     * @param children The children.
     *
     * @return The created term.
     */
    inline fun <reified T: ITerm> createTerm(constructor: TermConstructorOfT<T>, children: List<ITerm>): T {
        return createTerm(constructor as ITermConstructor, children) as? T
                ?: throw IllegalStateException("The builder for $constructor did not return a term of type ${T::class.java.name}.")
    }

    /**
     * Registers a term builder with this term factory.
     *
     * This overwrites any previously registered builder.
     *
     * @param constructor The term constructor.
     * @param builder The term builder.
     */
    abstract fun registerBuilder(constructor: ITermConstructor, builder: (ITermConstructor, List<ITerm>) -> ITerm)

    /**
     * Unregisters a term builder from this term factory.
     *
     * @param constructor The term constructor.
     */
    abstract fun unregisterBuilder(constructor: ITermConstructor)

}
