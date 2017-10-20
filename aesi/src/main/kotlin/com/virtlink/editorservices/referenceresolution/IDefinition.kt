package com.virtlink.editorservices.referenceresolution

import com.virtlink.editorservices.symbols.ISymbol
import java.io.Serializable

/**
 * A definition.
 */
interface IDefinition : Serializable {

    /**
     * Gets the symbol of the definition.
     */
    val symbol: ISymbol

}