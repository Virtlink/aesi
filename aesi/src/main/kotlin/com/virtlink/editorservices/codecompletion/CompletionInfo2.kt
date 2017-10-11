package com.virtlink.editorservices.codecompletion

class CompletionInfo2(
        override val prefix: String,
        override val proposals: List<ICompletionProposal2>)
    : ICompletionInfo2