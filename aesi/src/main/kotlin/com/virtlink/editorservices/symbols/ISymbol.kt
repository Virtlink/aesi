package com.virtlink.editorservices.symbols

import com.virtlink.editorservices.IDocument
import com.virtlink.editorservices.IProject
import java.io.Serializable

/**
 * A symbol.
 */
interface ISymbol: Serializable {

    /**
     * Gets the project that contains the symbol.
     */
    val project: IProject

    /**
     * Gets the document that contains this symbol.
     *
     * Can be `null` when the symbol is not tied to a particular document.
     */
    val document: IDocument?

    /**
     * Gets the range of the symbol's name in the document, if any.
     */
    val range: Span?

    /**
     * Gets the display name of the symbol.
     */
    val label: String

    /**
     * Gets a short description of the symbol, if any.
     */
    val description: String?

    /**
     * Gets the documentation of the symbol, if any.
     */
    val documentation: String?

    /**
     * Gets the type of the symbol.
     *
     * This is, for example, the type of a field or variable.
     */
    val type: String?

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