package com.virtlink.editorservices.types

import com.virtlink.editorservices.codecompletion.ICompletionProposal
import com.virtlink.editorservices.symbols.ISymbol

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
     * @param proposal The completion proposal.
     * @return The type symbol; or null when there is no type.
     */
    fun getType(proposal: ICompletionProposal): ISymbol?

}