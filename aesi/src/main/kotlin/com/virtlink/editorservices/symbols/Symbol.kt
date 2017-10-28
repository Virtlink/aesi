package com.virtlink.editorservices.symbols

import com.virtlink.editorservices.Span
import java.net.URI

data class Symbol(
        override val label: String,
        override val resource: URI? = null,
        override val nameRange: Span? = null,
        override val kind: String? = null,
        override val attributes: List<String> = emptyList())
    : ISymbol {

    override fun toString(): String
            = this.label

}