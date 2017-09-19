package com.virtlink.editorservices.codecompletion

interface ICompletionInfo {
    val prefix: String
    val proposals: List<ICompletionProposal>
}