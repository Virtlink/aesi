package com.virtlink.editorservices.structureoutline

import com.virtlink.editorservices.symbols.ISymbol
import java.io.Serializable

/**
 * A node in the structure outline.
 */
interface IStructureTreeNode : Serializable {

    /**
     * Gets the symbol this node refers to.
     */
    val symbol: ISymbol

}