package com.virtlink.paplj.terms

/**
 * A term.
 */
interface ITerm {

    /**
     * Gets the term constructor for this term.
     */
    val constructor: ITermConstructor

    /**
     * Gets the children of the term.
     */
    val children: List<ITerm>

}