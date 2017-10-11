package com.virtlink.editorservices.codecompletion

@Deprecated("Replaced by CompletionInfo2")
class CompletionInfo(
        override val prefix: String,
        override val proposals: List<ICompletionProposal>)
    : ICompletionInfo