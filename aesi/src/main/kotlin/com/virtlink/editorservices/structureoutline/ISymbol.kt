package com.virtlink.editorservices.structureoutline


@Deprecated("Removed")
interface ISymbol {
    val name: String
    val kind: SymbolKind
    val location: Int
}