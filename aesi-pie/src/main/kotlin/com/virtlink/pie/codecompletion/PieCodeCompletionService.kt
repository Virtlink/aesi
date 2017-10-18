package com.virtlink.pie.codecompletion

<<<<<<< Updated upstream
import com.google.inject.Inject
=======
import com.google.inject.name.Named
import com.virtlink.dummy.DummyCodeCompletionBuilder
>>>>>>> Stashed changes
import com.virtlink.editorservices.ICancellationToken
import com.virtlink.editorservices.IDocument
import com.virtlink.editorservices.IProject
import com.virtlink.editorservices.codecompletion.ICodeCompletionService
import com.virtlink.editorservices.codecompletion.ICompletionInfo2
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

    data class Input(val project: IProject,
                     val document: IDocument,
                     val caretOffset: Int,
                     val cancellationToken: ICancellationToken?)
        : Serializable

    override fun getCompletionInfo(
            project: IProject,
            document: IDocument,
            caretOffset: Int,
            cancellationToken: ICancellationToken?):
            ICompletionInfo2 {
        val input = PieCodeCompletionService.Input(
                project, document, caretOffset, cancellationToken
        )
        val app = BuildApp<PieCodeCompletionService.Input, ICompletionInfo2>(this.builderId, input)
        val manager = buildManagerProvider.getBuildManager(project)
        return manager.build(app)
    }
}