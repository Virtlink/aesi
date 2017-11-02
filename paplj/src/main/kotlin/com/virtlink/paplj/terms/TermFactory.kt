package com.virtlink.paplj.terms

/**
 * Base class for term factories.
 */
abstract class TermFactory {

    /** The registered term builders. */
    private val termBuilders: HashMap<ITermConstructor, (ITermConstructor, List<Term>) -> Term> = hashMapOf()

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
     * Creats a list with elements of the specified type.
     *
     * @param elements The elements in the list.
     * @return The list.
     */
    abstract fun <T: Term> createList(elements: List<T>): ListTerm<T>

    /**
     * Creates a term with the specified constructor and children.
     *
     * @param constructor The constructor.
     * @param children The children.
     *
     * @return The created term.
     */
    abstract fun createTerm(constructor: ITermConstructor, children: List<Term>): Term

    /**
     * Creates a term with the specified name and children.
     *
     * @param name The constructor name.
     * @param children The children.
     * @return The created term.
     */
    @Suppress("NOTHING_TO_INLINE")
    inline fun createTerm(name: String, vararg children: Term): Term
            = createTerm(name, children.asList())

    /**
     * Creates a term with the specified name and children.
     *
     * @param name The constructor name.
     * @param children The children.
     * @return The created term.
     */
    @Suppress("NOTHING_TO_INLINE")
    inline fun createTerm(name: String, children: List<Term>): Term
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
    inline fun createTerm(constructor: ITermConstructor, vararg children: Term): Term
            = createTerm(constructor, children.asList())

    /**
     * Creates a term with the specified constructor and children.
     *
     * @param constructor The constructor.
     * @param children The children.
     *
     * @return The created term.
     */
    inline fun <reified T: Term> createTerm(constructor: TermConstructorOfT<T>, vararg children: Term): T {
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
    inline fun <reified T: Term> createTerm(constructor: TermConstructorOfT<T>, children: List<Term>): T {
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
    fun registerBuilder(constructor: ITermConstructor, builder: (ITermConstructor, List<Term>) -> Term) {
        this.termBuilders.put(constructor, builder)
    }

    /**
     * Unregisters a term builder from this term factory.
     *
     * @param constructor The term constructor.
     */
    fun unregisterBuilder(constructor: ITermConstructor) {
        this.termBuilders.remove(constructor)
    }

    /**
     * Gets the term builder with the specified term constructor.
     *
     * @param constructor The term constructor.
     * @return The term builder; or null when not found.
     */
    protected fun getBuilder(constructor: ITermConstructor): ((ITermConstructor, List<Term>) -> Term)? {
        return this.termBuilders[constructor]
    }

}
