package com.virtlink.editorservices.codecompletion

import com.virtlink.editorservices.IVisibility
import com.virtlink.editorservices.Kind
import com.virtlink.editorservices.symbols.ISymbol
import java.io.Serializable
import java.util.*

/**
 * A completion proposal.
 */
interface ICompletionProposal2 : ISymbol, Serializable {

    /**
     * Gets the text to insert when the completion is accepted.
     */
    val insertionText: String?

    /**
     * Gets the offset of the caret after the text has been inserted,
     * relative to the start of the inserted text;
     * or null to put the caret after the inserted text.
     */
    val caret: Int?
}