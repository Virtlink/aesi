package com.virtlink.editorservices.codefolding

import com.virtlink.editorservices.ICancellationToken
import com.virtlink.editorservices.IDocument
import com.virtlink.editorservices.IProject

/**
 * Provides information on the foldable regions in a document.
 */
interface ICodeFoldingService {
    /**
     * Provides folding information about a document.
     *
     * @param project The project that contains the document.
     * @param document The document to provide folding information for.
     * @param cancellationToken The cancellation token; or `null` when not supported.
     * @return Folding info.
     */
    fun getFoldingRegions(
            project: IProject,
            document: IDocument,
            cancellationToken: ICancellationToken?)
            : List<IFoldingInfo>
}