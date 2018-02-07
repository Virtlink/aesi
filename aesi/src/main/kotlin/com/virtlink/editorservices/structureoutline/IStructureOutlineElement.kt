package com.virtlink.editorservices.structureoutline

import com.virtlink.editorservices.ScopeNames
import com.virtlink.editorservices.Span
import com.virtlink.editorservices.symbols.ISymbol
import java.io.Serializable

/**
 * A node in the structure outline.
 */
interface IStructureOutlineElement : Serializable {

//    /**
//     * Gets the symbol this node refers to.
//     */
//    val symbol: ISymbol

    val label: String
    val nameSpan: Span?
    val scopes: ScopeNames
    val isLeaf: Boolean?

}