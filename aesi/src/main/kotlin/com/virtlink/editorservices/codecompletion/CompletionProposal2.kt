package com.virtlink.editorservices.codecompletion

import com.virtlink.editorservices.IDocument
import com.virtlink.editorservices.IProject

class CompletionProposal2(
        override val label: String,
        override val project: IProject,
        override val document: IDocument? = null,
        override val range: Span? = null,
        override val description: String? = null,
        override val documentation: String? = null,
        override val insertionText: String? = null,
        override val caret: Int? = null,
        override val type: String? = null,
        override val kind: String? = null,
        override val attributes: List<String> = emptyList())
    : ICompletionProposal2