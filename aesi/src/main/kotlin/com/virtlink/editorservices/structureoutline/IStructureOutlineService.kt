package com.virtlink.editorservices.structureoutline

import com.virtlink.editorservices.ICancellationToken
import java.net.URI

/**
 * Provides a structure outline for the given document.
 */
interface IStructureOutlineService {

    /**
     * Configures the service.
     */
    fun configure(configuration: IStructureOutlineConfiguration)

    /**
     * Returns the root nodes of the structure outline of the specified document.
     *
     * @param document The URI of the document.
     * @param cancellationToken The cancellation token.
     * @return A list of tree nodes, representing the root nodes of the structure outline tree.
     */
    fun getRoots(document: URI,
                 cancellationToken: ICancellationToken?)
        : IStructureOutlineInfo?

    /**
     * Returns the child nodes of the specified structure tree node of the specified document.
     *
     * @param document The URI of the document.
     * @param node The structure outline tree node.
     * @param cancellationToken The cancellation token.
     * @return A list of tree nodes, representing the root nodes of the structure outline tree.
     */
    fun getChildren(document: URI,
                    node: IStructureOutlineElement,
                    cancellationToken: ICancellationToken?)
            : IStructureOutlineInfo?

//    /**
//     * Returns whether the specified node has any child nodes.
//     *
//     * @param document The URI of the document.
//     * @param node The structure outline tree node.
//     * @return True when the node has child nodes, false when the node has no child nodes;
//     * and null when it is currently unknown whether the node has child nodes.
//     */
//    fun hasChildren(document: URI,
//                    node: IStructureOutlineElement)
//            : Boolean?

}