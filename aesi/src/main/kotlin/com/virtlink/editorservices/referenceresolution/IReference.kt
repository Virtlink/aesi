package com.virtlink.editorservices.referenceresolution

import com.virtlink.editorservices.symbols.ISymbol
import java.io.Serializable

/**
 * A reference.
 */
interface IReference : Serializable {

    /**
     * Gets the symbol of the reference.
     */
    val symbol: ISymbol

}