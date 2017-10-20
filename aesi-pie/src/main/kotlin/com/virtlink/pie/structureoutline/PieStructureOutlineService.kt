package com.virtlink.pie.structureoutline

import com.google.inject.name.Named
import com.virtlink.editorservices.ICancellationToken
import com.virtlink.editorservices.IDocument
import com.virtlink.editorservices.IProject
import com.virtlink.editorservices.structureoutline.IStructureOutlineService
import com.virtlink.editorservices.structureoutline.IStructureTreeNode
import com.virtlink.editorservices.symbols.ISymbol
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

    data class StructureTreeNode(override val symbol: ISymbol, val children: List<StructureTreeNode>): IStructureTreeNode

    data class StructureTree(val roots: List<StructureTreeNode>): Serializable

    override fun getRootNodes(
            project: IProject,
            document: IDocument,
            cancellationToken: ICancellationToken?)
            : List<IStructureTreeNode> {
        return getTree(project, document).roots
    }

    override fun getChildNodes(
            project: IProject,
            document: IDocument,
            node: IStructureTreeNode,
            cancellationToken: ICancellationToken?)
            : List<IStructureTreeNode> {
        return (node as StructureTreeNode).children
    }

    private fun getTree(project: IProject, document: IDocument): StructureTree {
        val input = PieStructureOutlineService.Input(document)
        val app = BuildApp<PieStructureOutlineService.Input, StructureTree>(this.builderId, input)
        val manager = buildManagerProvider.getBuildManager(project)
        return manager.build(app)
    }
}