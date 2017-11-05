package com.virtlink.terms

/**
 * Any term.
 */
class AnyTerm(
        override val constructor: ITermConstructor,
        override val children: List<ITerm>)
    : Term()