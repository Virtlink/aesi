package com.virtlink.editorservices.structureoutline

import com.virtlink.editorservices.ICancellationToken
import com.virtlink.editorservices.IDocument
import com.virtlink.editorservices.IDocumentLocation
import com.virtlink.editorservices.IProject
import com.virtlink.editorservices.codecompletion.ICompletionProposal

@Deprecated("Replaced by IStructureOutlineService")
interface IStructureOutliner {

    /**
     * Returns the child symbols of the given symbol (if any)
     * or the root of the document.
     */
    fun outline(project: IProject,
                document: IDocument,
                symbol: ISymbol?,
                cancellationToken: ICancellationToken?)
            : List<ISymbol>

}