package com.virtlink.editorservices.codefolding

import com.virtlink.editorservices.ICancellationToken
import java.net.URI

/**
 * Provides information on the foldable regions in a document.
 */
interface ICodeFoldingService {
    /**
     * Provides folding information about a document.
     *
     * @param document The URI of the document to provide folding information for.
     * @param cancellationToken The cancellation token.
     * @return Folding info.
     */
    fun getFoldingRegions(
            document: URI,
            cancellationToken: ICancellationToken)
            : List<IFoldingInfo>
}