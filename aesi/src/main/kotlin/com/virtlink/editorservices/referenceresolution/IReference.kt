package com.virtlink.editorservices.referenceresolution

import com.virtlink.editorservices.IDocument
import com.virtlink.editorservices.IProject
import com.virtlink.editorservices.symbols.ISymbol

/**
 * A reference.
 */
interface IReference {

    /**
     * Gets the symbol of the reference.
     */
    val symbol: ISymbol

}