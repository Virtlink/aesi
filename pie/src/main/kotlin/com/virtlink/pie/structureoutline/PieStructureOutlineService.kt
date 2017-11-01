package com.virtlink.pie.structureoutline

import com.google.inject.name.Named
import com.virtlink.editorservices.ICancellationToken
import com.virtlink.editorservices.resources.IResourceManager
import com.virtlink.editorservices.structureoutline.IStructureOutlineService
import com.virtlink.editorservices.structureoutline.IStructureTreeNode
import com.virtlink.editorservices.symbols.ISymbol
import com.virtlink.pie.IBuildManagerProvider
import mb.pie.runtime.core.BuildApp
import java.io.Serializable
import java.net.URI

class PieStructureOutlineService(
        private val buildManagerProvider: IBuildManagerProvider,
        private val resourceManager: IResourceManager,
        @Named(PieStructureOutlineService.ID) private val builderId: String
) : IStructureOutlineService {

    companion object {
        const val ID: String = "StructureOutlineBuilder.ID"
    }

    data class Input(val document: URI) : Serializable

    data class StructureTreeNode(override val symbol: ISymbol, val children: List<StructureTreeNode>): IStructureTreeNode

    data class StructureTree(val roots: List<StructureTreeNode>): Serializable

    override fun getRootNodes(
            document: URI,
            cancellationToken: ICancellationToken)
            : List<IStructureTreeNode> {
        return getTree(document).roots
    }

    override fun getChildNodes(
            document: URI,
            node: IStructureTreeNode,
            cancellationToken: ICancellationToken)
            : List<IStructureTreeNode> {
        return (node as StructureTreeNode).children
    }

    override fun hasChildren(
            document: URI,
            node: IStructureTreeNode)
            : Boolean? {
        return null
    }

    private fun getTree(document: URI): StructureTree {
        val input = PieStructureOutlineService.Input(document)
        val project = this.resourceManager.getProjectOf(document)!!
        val app = BuildApp<PieStructureOutlineService.Input, StructureTree>(this.builderId, input)
        val manager = buildManagerProvider.getBuildManager(project)
        return manager.build(app)
    }
}