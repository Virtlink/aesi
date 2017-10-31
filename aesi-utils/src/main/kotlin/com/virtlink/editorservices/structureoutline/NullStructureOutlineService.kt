package com.virtlink.editorservices.structureoutline

import com.virtlink.editorservices.ICancellationToken
import java.net.URI

/**
 * Null implementation for when there is no actual implementation available.
 */
class NullStructureOutlineService : IStructureOutlineService {
    override fun getRootNodes(document: URI, cancellationToken: ICancellationToken): List<IStructureTreeNode>
            = emptyList()

    override fun getChildNodes(document: URI, node: IStructureTreeNode, cancellationToken: ICancellationToken): List<IStructureTreeNode>
            = emptyList()

    override fun hasChildren(document: URI, node: IStructureTreeNode): Boolean?
            = false
}