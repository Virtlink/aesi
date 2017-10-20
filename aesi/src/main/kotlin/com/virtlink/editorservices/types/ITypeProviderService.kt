package com.virtlink.editorservices.types

import com.virtlink.editorservices.IDocument
import com.virtlink.editorservices.IProject
import com.virtlink.editorservices.codecompletion.ICompletionProposal
import com.virtlink.editorservices.symbols.ISymbol

interface ITypeProviderService {

    /**
     * Gets the type for the specified symbol.
     *
     * @param project The project that contains the symbol.
     * @param symbol The symbol.
     * @return The type symbol; or null when there is no type.
     */
    fun getType(project: IProject, symbol: ISymbol): ISymbol?

    /**
     * Gets the type for the specified completion proposal.
     *
     * @param project The project that contains the document.
     * @param document The document for which the proposal was created.
     * @param proposal The completion proposal.
     * @return The type symbol; or null when there is no type.
     */
    fun getType(project: IProject, document: IDocument, proposal: ICompletionProposal): ISymbol?

}