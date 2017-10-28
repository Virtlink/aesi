package com.virtlink.editorservices.symbols

import com.virtlink.editorservices.Span
import java.io.Serializable
import java.net.URI

/**
 * A symbol.
 */
interface ISymbol : Serializable {

    /**
     * Gets the resource that contains this symbol;
     * or null when the symbol is not in a particular resource.
     *
     * When the symbol is in a document, this must be that document's URI.
     * When the symbol is in a project but not a particular document, this must be that project's URI.
     * When the symbol is in neither, the value is null.
     */
    val resource: URI?

    /**
     * Gets the range of the symbol's name in the document, if any.
     *
     * Should be null when the symbol is not contained in a document.
     */
    val nameRange: Span?

    /**
     * Gets the display name of the symbol.
     */
    val label: String

    // Open questions: kind a string and attributes a list of strings
    // or kind a list of strings (attributes and kind)
    // or kind a space-separated list of strings (attributes and kind) as a string
    // or leave out attributes completely (but how to encode visibility, extensibility)

    /**
     * Gets the kind of symbol.
     *
     * For example: "meta.function" to indicate a function.
     */
    val kind: String?

    /**
     * Gets the attributes that apply to the symbol.
     *
     * This is an unordered list of names, each of which
     * indicates an attribute of the completion proposal.
     * For example "meta.static" and "meta.public" to
     * indicate the element is public and static, for
     * example a public static function.
     */
    val attributes: List<String>
}