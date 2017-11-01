package com.virtlink.editorservices.structureoutline

import com.virtlink.editorservices.ICancellationToken
import java.net.URI

/**
 * Provides a structure outline for the given document.
 */
interface IStructureOutlineService {

    /**
     * Returns the root nodes of the structure outline of the specified document.
     *
     * @param document The URI of the document.
     * @param cancellationToken The cancellation token.
     * @return A list of tree nodes, representing the root nodes of the structure outline tree.
     */
    fun getRootNodes(document: URI,
                     cancellationToken: ICancellationToken)
        : List<IStructureTreeNode>

    /**
     * Returns the child nodes of the specified structure tree node of the specified document.
     *
     * @param document The URI of the document.
     * @param node The structure outline tree node.
     * @param cancellationToken The cancellation token.
     * @return A list of tree nodes, representing the root nodes of the structure outline tree.
     */
    fun getChildNodes(document: URI,
                      node: IStructureTreeNode,
                      cancellationToken: ICancellationToken)
            : List<IStructureTreeNode>

    /**
     * Returns whether the specified node has any child nodes.
     *
     * @param document The URI of the document.
     * @param node The structure outline tree node.
     * @return True when the node has child nodes, false when the node has no child nodes;
     * and null when it is currently unknown whether the node has child nodes.
     */
    fun hasChildren(document: URI,
                    node: IStructureTreeNode)
            : Boolean?

}