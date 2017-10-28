package com.virtlink.editorservices.types

import com.virtlink.editorservices.codecompletion.ICompletionProposal
import com.virtlink.editorservices.symbols.ISymbol
import java.net.URI

interface ITypeProviderService {

    /**
     * Gets the type for the specified symbol.
     *
     * @param symbol The symbol.
     * @return The type symbol; or null when there is no type.
     */
    fun getType(symbol: ISymbol): ISymbol?

    /**
     * Gets the type for the specified completion proposal.
     *
     * @param document The URI of the document for which the proposal was created.
     * @param proposal The completion proposal.
     * @return The type symbol; or null when there is no type.
     */
    fun getType(document: URI, proposal: ICompletionProposal): ISymbol?

}