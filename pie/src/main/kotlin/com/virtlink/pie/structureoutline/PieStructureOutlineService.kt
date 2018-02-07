package com.virtlink.pie.structureoutline

import com.google.inject.name.Named
import com.virtlink.editorservices.ICancellationToken
import com.virtlink.editorservices.ScopeNames
import com.virtlink.editorservices.Span
import com.virtlink.editorservices.resources.IResourceManager
import com.virtlink.editorservices.structureoutline.*
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

    data class StructureOutlineElement(
            val children: List<StructureOutlineElement>,
            override val label: String,
            override val nameSpan: Span? = null,
            override val scopes: ScopeNames = "",
            override val isLeaf: Boolean? = null)
        : IStructureOutlineElement

    data class StructureTree(val roots: List<StructureOutlineElement>): Serializable

    override fun configure(configuration: IStructureOutlineConfiguration) {
        // Nothing to do.
    }

    override fun getRoots(
            document: URI,
            cancellationToken: ICancellationToken?)
            : IStructureOutlineInfo? {
        return StructureOutlineInfo(getTree(document).roots)
    }

    override fun getChildren(
            document: URI,
            node: IStructureOutlineElement,
            cancellationToken: ICancellationToken?)
            : IStructureOutlineInfo? {
        return StructureOutlineInfo((node as StructureOutlineElement).children)
    }

    private fun getTree(document: URI): StructureTree {
        val input = PieStructureOutlineService.Input(document)
        val project = this.resourceManager.getProjectOf(document)!!
        val app = BuildApp<PieStructureOutlineService.Input, StructureTree>(this.builderId, input)
        val manager = buildManagerProvider.getBuildManager(project)
        return manager.build(app)
    }
}