package com.virtlink.pie.codecompletion

import com.google.inject.Inject
import com.virtlink.editorservices.ICancellationToken
import com.virtlink.editorservices.IDocument
import com.virtlink.editorservices.IProject
import com.virtlink.editorservices.codecompletion.ICodeCompletionService
import com.virtlink.editorservices.codecompletion.ICompletionInfo2
import com.virtlink.pie.IBuildManagerProvider
import mb.pie.runtime.core.BuildApp

class PieCodeCompletionService @Inject constructor(
        private val buildManagerProvider: IBuildManagerProvider
) : ICodeCompletionService {

    override fun getCompletionInfo(
            project: IProject,
            document: IDocument,
            caretOffset: Int,
            cancellationToken: ICancellationToken?):
            ICompletionInfo2 {

        val builderId = PieCodeCompletionBuilder.id
        val input = PieCodeCompletionBuilder.Input(
                project, document, caretOffset
        )
        val app = BuildApp<PieCodeCompletionBuilder.Input, ICompletionInfo2>(builderId, input)
        val manager = buildManagerProvider.getBuildManager(project)
        return manager.build(app)
    }
}