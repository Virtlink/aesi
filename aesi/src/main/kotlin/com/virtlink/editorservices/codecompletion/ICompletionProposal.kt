package com.virtlink.editorservices.codecompletion

import com.virtlink.editorservices.Offset
import com.virtlink.editorservices.ScopeNames
import java.io.Serializable

/**
 * A completion proposal.
 */
interface ICompletionProposal : Serializable {

    /**
     * Gets the text to insert when the completion is accepted.
     */
    val content: String?

    /**
     * Gets the display name of the proposal.
     */
    val label: String

    /**
     * Gets the description of the proposal.
     */
    val description: String?

    /**
     * Gets the right-hand label of the proposal.
     */
    val rightLabel: String?

    /**
     * Gets the kind of proposal.
     *
     * For example: "meta.function" to indicate a function.
     */
    val scopes: ScopeNames

    /**
     * Gets the commit characters.
     */
    val commitCharacters: List<Char>
}