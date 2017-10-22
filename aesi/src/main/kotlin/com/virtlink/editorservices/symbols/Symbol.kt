package com.virtlink.editorservices.symbols

import com.virtlink.editorservices.IDocument
import com.virtlink.editorservices.IProject
import com.virtlink.editorservices.Span

data class Symbol(
        override val label: String,
        override val project: IProject? = null,
        override val document: IDocument? = null,
        override val nameRange: Span? = null,
        override val kind: String? = null,
        override val attributes: List<String> = emptyList())
    : ISymbol {

    override fun toString(): String
            = this.label

}