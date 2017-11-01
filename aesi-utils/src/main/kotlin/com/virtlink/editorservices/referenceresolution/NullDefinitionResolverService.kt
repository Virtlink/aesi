package com.virtlink.editorservices.referenceresolution

import com.google.inject.Inject
import com.virtlink.editorservices.ICancellationToken
import com.virtlink.editorservices.Offset
import java.net.URI

/**
 * Null implementation for when there is no actual implementation available.
 */
class NullDefinitionResolverService @Inject constructor()
    : IDefinitionResolverService {

    override fun resolve(document: URI, caretOffset: Offset, cancellationToken: ICancellationToken): IDefinitionResolutionInfo?
            = null

}