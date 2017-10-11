package com.virtlink.editorservices.structureoutline

import com.virtlink.editorservices.DocumentLocation
import com.virtlink.editorservices.Location


@Deprecated("Removed")
interface ISymbol {
    val name: String
    val kind: SymbolKind
    val location: Int
}