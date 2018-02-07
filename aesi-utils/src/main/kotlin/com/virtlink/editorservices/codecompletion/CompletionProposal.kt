package com.virtlink.editorservices.codecompletion

import com.virtlink.editorservices.Offset
import com.virtlink.editorservices.ScopeNames

class CompletionProposal(
        override val label: String,
        override val content: String? = null,
        override val description: String? = null,
        override val scopes: ScopeNames = ScopeNames(),
        override val rightLabel: String? = null,
        override val commitCharacters: List<Char> = emptyList(),
        val caret: Offset? = null)
    : ICompletionProposal