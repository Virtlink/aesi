package com.virtlink.editorservices.syntaxcoloring

import com.virtlink.editorservices.Span

/**
 * A coloring token.
 */
interface IToken2 {
    /**
     * The span in the document that is being colored.
     */
    val location: Span

    /**
     * The name assigned to the token.
     *
     * The name is used to find an appropriate color for the token.
     */
    val name: String

    /**
     * Gets the attributes that apply to the token.
     *
     * This is an unordered list of names, each of which
     * indicates an attribute of the token.
     * For example "invalid.deprecated" to
     * indicate the token should display
     * as a deprecated element.
     */
    val attributes: List<String>
}