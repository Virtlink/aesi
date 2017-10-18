package com.virtlink.pie.structureoutline

import com.google.inject.name.Named
import com.virtlink.editorservices.ICancellationToken
import com.virtlink.editorservices.IDocument
import com.virtlink.editorservices.IProject
import com.virtlink.editorservices.codecompletion.ICompletionInfo
import com.virtlink.editorservices.structureoutline.IStructureOutlineService
import com.virtlink.editorservices.structureoutline.IStructureTreeNode
import com.virtlink.pie.IBuildManagerProvider
import mb.pie.runtime.core.BuildApp
import java.io.Serializable

class PieStructureOutlineService(
        private val buildManagerProvider: IBuildManagerProvider,
        @Named(PieStructureOutlineService.ID) private val builderId: String
) : IStructureOutlineService {

    companion object {
        const val ID: String = "StructureOutlineBuilder.ID"
    }

    data class Input(val document: IDocument) : Serializable

    override fun getRootNodes(
            project: IProject,
            document: IDocument,
            cancellationToken: ICancellationToken?)
            : List<IStructureTreeNode> {

    }

    override fun getChildNodes(
            project: IProject,
            document: IDocument,
            node: IStructureTreeNode,
            cancellationToken: ICancellationToken?)
            : List<IStructureTreeNode> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun getTree(document: IDocument) {
        val input = PieStructureOutlineService.Input(document)
        val app = BuildApp<PieStructureOutlineService.Input, ICompletionInfo>(this.builderId, input)
        val manager = buildManagerProvider.getBuildManager(document.project)
        return manager.build(app)
    }
}