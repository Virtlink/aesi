package com.virtlink.paplj.structureoutline

import com.virtlink.editorservices.ICancellationToken
import com.virtlink.editorservices.IDocument
import com.virtlink.editorservices.IProject
import com.virtlink.editorservices.structureoutline.*
import com.virtlink.editorservices.symbols.ISymbol
import com.virtlink.editorservices.symbols.Symbol
import com.virtlink.logging.logger

class DummyStructureOutliner: IStructureOutlineService {

    @Suppress("PrivatePropertyName")
    private val LOG by logger()

    override fun getRootNodes(project: IProject, document: IDocument, cancellationToken: ICancellationToken?): List<IStructureTreeNode>
            = listOf(rootSymbol)

    override fun getChildNodes(project: IProject, document: IDocument, node: IStructureTreeNode, cancellationToken: ICancellationToken?): List<IStructureTreeNode>
            = (node as Node).children

    override fun hasChildren(project: IProject, document: IDocument, node: IStructureTreeNode)
            = null

    private val rootSymbol: Node = Node(Symbol(label="root", kind="constant"), listOf(
            Node(Symbol(label="file1", kind="file"), listOf(
                    Node(Symbol(label="myClass0", kind="class"), listOf(
                            Node(Symbol(label="myFunc", kind="function"), listOf())
                    )),
                    Node(Symbol(label="myClass2", kind="class"), listOf()),
                    Node(Symbol(label="myClass4", kind="class"), listOf())
            )),
            Node(Symbol(label="file2", kind="file"), listOf())
    ))

    private data class Node(
            override val symbol: ISymbol,
            val children: List<Node>) : IStructureTreeNode

}