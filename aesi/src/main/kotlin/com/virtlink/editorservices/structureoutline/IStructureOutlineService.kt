package com.virtlink.editorservices.structureoutline

import com.virtlink.editorservices.ICancellationToken
import com.virtlink.editorservices.IDocument
import com.virtlink.editorservices.IProject

/**
 * Provides a structure outline for the given document.
 */
interface IStructureOutlineService {

    /**
     * Returns the root nodes of the structure outline of the specified document.
     *
     * @param project The project that contains the document.
     * @param document The document.
     * @param cancellationToken The cancellation token; or `null` when not supported.
     * @return A list of tree nodes, representing the root nodes of the structure outline tree.
     */
    fun getRootNodes(project: IProject,
                     document: IDocument,
                     cancellationToken: ICancellationToken?)
        : List<IStructureTreeNode>

    /**
     * Returns the child nodes of the specified structure tree node of the specified document.
     *
     * @param project The project that contains the document.
     * @param document The document.
     * @param node The structure outline tree node.
     * @param cancellationToken The cancellation token; or `null` when not supported.
     * @return A list of tree nodes, representing the root nodes of the structure outline tree.
     */
    fun getChildNodes(project: IProject,
                      document: IDocument,
                      node: IStructureTreeNode,
                      cancellationToken: ICancellationToken?)
            : List<IStructureTreeNode>

}