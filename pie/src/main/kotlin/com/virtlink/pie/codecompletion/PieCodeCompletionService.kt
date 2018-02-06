package com.virtlink.pie.codecompletion

import com.google.inject.Inject
import com.google.inject.name.Named
import com.virtlink.editorservices.ICancellationToken
import com.virtlink.editorservices.Offset
import com.virtlink.editorservices.codecompletion.ICodeCompletionService
import com.virtlink.editorservices.codecompletion.ICompletionInfo
import com.virtlink.editorservices.resources.IResourceManager
import com.virtlink.pie.IBuildManagerProvider
import mb.pie.runtime.core.BuildApp
import java.io.Serializable
import java.net.URI

class PieCodeCompletionService @Inject constructor(
        private val buildManagerProvider: IBuildManagerProvider,
        private val resourceManager: IResourceManager,
        @Named(PieCodeCompletionService.id) private val builderId: String
) : ICodeCompletionService {

    companion object {
        const val id: String = "CodeCompletionBuilder.ID"
    }

    data class Input(val document: URI,
                     val caretOffset: Offset)
        : Serializable

    override val triggerCharacters: List<Char>
        get() = emptyList()

    override fun getCompletionInfo(
            document: URI,
            caretOffset: Offset,
            cancellationToken: ICancellationToken?):
            ICompletionInfo {
        val input = PieCodeCompletionService.Input(
                document, caretOffset
        )
        val project = this.resourceManager.getProjectOf(document)!!
        val app = BuildApp<PieCodeCompletionService.Input, ICompletionInfo>(this.builderId, input)
        val manager = this.buildManagerProvider.getBuildManager(project)
        return manager.build(app)
    }
}