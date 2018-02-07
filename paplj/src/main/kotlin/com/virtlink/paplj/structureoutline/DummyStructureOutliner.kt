package com.virtlink.paplj.structureoutline

import com.virtlink.editorservices.ICancellationToken
import com.virtlink.editorservices.ScopeNames
import com.virtlink.editorservices.Span
import com.virtlink.editorservices.structureoutline.*
import com.virtlink.editorservices.symbols.ISymbol
import com.virtlink.editorservices.symbols.Symbol
import com.virtlink.logging.logger
import java.net.URI

class DummyStructureOutliner: IStructureOutlineService {

    @Suppress("PrivatePropertyName")
    private val LOG by logger()

    override fun configure(configuration: IStructureOutlineConfiguration) {
        // Nothing to do.
    }

    override fun getRoots(document: URI, cancellationToken: ICancellationToken?): IStructureOutlineInfo
            = StructureOutlineInfo(listOf(rootSymbol))

    override fun getChildren(document: URI, node: IStructureOutlineElement, cancellationToken: ICancellationToken?): IStructureOutlineInfo
            = StructureOutlineInfo((node as Node).children)

    private val rootSymbol: Node = Node(label = "root", scopes = "constant", children = listOf(
            Node(label = "file1", scopes = "file", children = listOf(
                    Node(label = "myClass0", scopes = "class", children = listOf(
                            Node(label = "myFunc", scopes = "function", children = listOf())
                    )),
                    Node(label = "myClass2", scopes = "class", children = listOf()),
                    Node(label = "myClass4", scopes = "class", children = listOf())
            )),
            Node(label = "file2", scopes = "file", children = listOf())
    ))

    private data class Node(
            val children: List<Node>,
            override val label: String,
            override val nameSpan: Span? = null,
            override val scopes: ScopeNames = "",
            override val isLeaf: Boolean? = null)
        : IStructureOutlineElement

}