package com.virtlink.editorservices.codecompletion

import com.virtlink.editorservices.Offset
import java.io.Serializable

/**
 * A completion proposal.
 */
interface ICompletionProposal : Serializable {

    /**
     * Gets the display name of the proposal.
     */
    val label: String

    /**
     * Gets the description of the proposal.
     */
    val description: String?

    /**
     * Gets the kind of proposal.
     *
     * For example: "meta.function" to indicate a function.
     */
    val kind: String?

    /**
     * Gets the attributes that apply to the proposal.
     *
     * This is an unordered list of names, each of which
     * indicates an attribute of the completion proposal.
     * For example "meta.static" and "meta.public" to
     * indicate the element is public and static, for
     * example a public static function.
     */
    val attributes: List<String>

    /**
     * Gets the text to insert when the completion is accepted.
     */
    val insertionText: String?

    /**
     * Gets the offset of the caret after the text has been inserted,
     * relative to the start of the inserted text;
     * or null to put the caret after the inserted text.
     */
    val caret: Offset?
}