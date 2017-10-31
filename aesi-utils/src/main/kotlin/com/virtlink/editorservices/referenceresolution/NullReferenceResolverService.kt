package com.virtlink.editorservices.referenceresolution

import com.virtlink.editorservices.ICancellationToken
import com.virtlink.editorservices.Offset
import java.net.URI

/**
 * Null implementation for when there is no actual implementation available.
 */
class NullReferenceResolverService: IReferenceResolverService {

    override fun resolve(document: URI, caretOffset: Offset, cancellationToken: ICancellationToken): IReferenceResolutionInfo?
            = null

}