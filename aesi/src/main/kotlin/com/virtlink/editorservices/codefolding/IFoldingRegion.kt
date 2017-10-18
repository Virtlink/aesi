package com.virtlink.editorservices.codefolding

import com.virtlink.editorservices.Span
import java.io.Serializable

/**
 * Describes a foldable region of code.
 */
interface IFoldingRegion : Serializable {

    /**
     * Gets the region of code that can be collapsed.
     */
    val span: Span

    /**
     * Gets the label to display in place of the folded code.
     */
    val label: String?

    /**
     * A short description of the folded region.
     *
     * Some editors display this as a tooltip above the collapsed region.
     */
    val description: String?

    /**
     * Gets the default state of the region: collapsed or expanded, or any.
     */
    val defaultState: FoldingState

    /**
     * Specifies the state of the foldable region.
     */
    enum class FoldingState {
        None,
        Collapsed,
        Expanded,
    }
}