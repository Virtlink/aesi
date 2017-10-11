package com.virtlink.editorservices.codecompletion

import com.virtlink.editorservices.Kind
import com.virtlink.editorservices.IVisibility
import java.util.*

/**
 * A completion proposal.
 */
@Deprecated("Replaced by ICompletionProposal2")
interface ICompletionProposal {

    /**
     * The label displayed to the user.
     */
    val label: String

    /**
     * Description of the proposal.
     */
    val description: String?

    /**
     * Documentation of the proposal.
     */
    val documentation: String?

    /**
     * Whether the text match is case-sensitive.
     */
    val caseSensitive: Boolean

    /**
     * The text to insert when the completion is accepted.
     */
    val insertionText: String?

    /**
     * The offset of the caret after the text has been inserted,
     * relative to the start of the inserted text;
     * or null to put the caret after the inserted text.
     */
    val caret: Int?

    /**
     * The postfix label displayed after the main label in the proposal list.
     */
    val postfix: String?

    /**
     * The type displayed next to the label in the proposal list.
     */
    val type: String?

    /**
     * The completion priority.
     */
    val priority: Int

    /**
     * The completion kind.
     */
    val kind: Kind

    /**
     * The completion visibility.
     */
    val visibility: IVisibility?

    /**
     * Completion attributes.
     */
    val attributes: EnumSet<Attribute>
}

