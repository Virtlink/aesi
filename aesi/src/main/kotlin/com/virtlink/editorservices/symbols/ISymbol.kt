package com.virtlink.editorservices.symbols

import com.virtlink.editorservices.IDocument
import com.virtlink.editorservices.IProject
import com.virtlink.editorservices.Span
import java.io.Serializable

/**
 * A symbol.
 */
interface ISymbol : Serializable {

    // Open question: add project for when document is null? Either<Project, Document>?
    // Symbol founds at a specific position in a specific document version,
    // or in a non-specific position in a document generally,
    // or in a non-specific document in a project generally,
    // ...

    /**
     * Gets the project that contains this symbol.
     */
    val project: IProject

    /**
     * Gets the document that contains this symbol;
     * or null when the symbol is not in a particular document.
     */
    val document: IDocument?

    /**
     * Gets the range of the symbol's name in the document, if any.
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