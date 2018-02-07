package com.virtlink.editorservices.structureoutline

import com.google.inject.Inject
import com.virtlink.editorservices.ICancellationToken
import java.net.URI

/**
 * Null implementation for when there is no actual implementation available.
 */
class NullStructureOutlineService @Inject constructor()
    : IStructureOutlineService {

    override fun configure(configuration: IStructureOutlineConfiguration) {
        // Nothing to do.
    }

    override fun getRoots(document: URI, cancellationToken: ICancellationToken?): IStructureOutlineInfo?
            = null

    override fun getChildren(document: URI, node: IStructureOutlineElement, cancellationToken: ICancellationToken?): IStructureOutlineInfo?
            = null

}