package com.virtlink.editorservices.codecompletion

class CompletionInfo(
        override val prefix: String,
        override val proposals: List<ICompletionProposal>)
    : ICompletionInfo