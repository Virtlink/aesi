package com.virtlink.editorservices.referenceresolution

import com.virtlink.editorservices.IDocument
import com.virtlink.editorservices.IProject
import com.virtlink.editorservices.symbols.ISymbol

/**
 * A definition.
 */
interface IDefinition {

    /**
     * Gets the symbol of the definition.
     */
    val symbol: ISymbol

}