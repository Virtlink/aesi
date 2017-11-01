package com.virtlink.editorservices.codefolding

import com.google.inject.Inject
import com.virtlink.editorservices.ICancellationToken
import java.net.URI

/**
 * Null implementation for when there is no actual implementation available.
 */
class NullCodeFoldingService @Inject constructor()
    : ICodeFoldingService {

    override fun getFoldingRegions(document: URI, cancellationToken: ICancellationToken): List<IFoldingInfo>
            = emptyList()

}