package com.virtlink.pie.codecompletion

import com.google.inject.Inject
import com.google.inject.name.Named
import com.virtlink.editorservices.ICancellationToken
import com.virtlink.editorservices.IDocument
import com.virtlink.editorservices.IProject
import com.virtlink.editorservices.Offset
import com.virtlink.editorservices.codecompletion.ICodeCompletionService
import com.virtlink.editorservices.codecompletion.ICompletionInfo
import com.virtlink.pie.IBuildManagerProvider
import mb.pie.runtime.core.BuildApp
import java.io.Serializable

class PieCodeCompletionService @Inject constructor(
        private val buildManagerProvider: IBuildManagerProvider,
        @Named(PieCodeCompletionService.ID) private val builderId: String
) : ICodeCompletionService {

    companion object {
        const val ID: String = "CodeCompletionBuilder.ID"
    }

    data class Input(val document: IDocument,
                     val caretOffset: Offset)
        : Serializable

    override fun getCompletionInfo(
            project: IProject,
            document: IDocument,
            caretOffset: Offset,
            cancellationToken: ICancellationToken?):
            ICompletionInfo {
        val input = PieCodeCompletionService.Input(
                document, caretOffset
        )
        val app = BuildApp<PieCodeCompletionService.Input, ICompletionInfo>(this.builderId, input)
        val manager = buildManagerProvider.getBuildManager(project)
        return manager.build(app)
    }
}