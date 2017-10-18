package com.virtlink.editorservices.syntaxcoloring

import com.virtlink.editorservices.Span
import java.io.Serializable

/**
 * A coloring token.
 */
interface IToken : Serializable {

    /**
     * Gets the span in the document that is being colored.
     */
    val location: Span

    /**
     * Gets the name assigned to the token.
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