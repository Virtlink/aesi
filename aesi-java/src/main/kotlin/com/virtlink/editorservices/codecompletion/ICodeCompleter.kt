package com.virtlink.editorservices.codecompletion

import com.virtlink.editorservices.ICancellationToken
import com.virtlink.editorservices.IDocumentLocation

interface ICodeCompleter {
    fun complete(project: com.virtlink.editorservices.IProject,
                 caret: IDocumentLocation,
                 smart: Boolean,
                 cancellationToken: ICancellationToken): List<ICompletionProposal>
}