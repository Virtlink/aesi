package com.virtlink.editorservices.codecompletion

import java.io.Serializable

/**
 * Completion info.
 */
interface ICompletionInfo : Serializable {
    /**
     * Gets the prefix up to the cursor that is being completed.
     *
     * Some editors use this prefix to highlight the word being completed.
     * Should be an empty string when there is no prefix.
     */
    val prefix: String

    /**
     * Gets a list of completion proposals to show to the user.
     *
     * The list MUST be in the order that the proposals should be shown to the user.
     * The list SHOULD only contain applicable entries (e.g. filtered by prefix).
     * When there is only one proposal, the editor MAY insert
     * that proposal without displaying the list of choices to the user.
     */
    val proposals: List<ICompletionProposal>
}