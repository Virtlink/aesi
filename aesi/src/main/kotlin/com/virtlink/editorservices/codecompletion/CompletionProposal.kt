package com.virtlink.editorservices.codecompletion

class CompletionProposal(
        override val label: String,
        override val description: String? = null,
        override val kind: String? = null,
        override val attributes: List<String> = emptyList(),
        override val insertionText: String? = null,
        override val caret: Int? = null)
    : ICompletionProposal