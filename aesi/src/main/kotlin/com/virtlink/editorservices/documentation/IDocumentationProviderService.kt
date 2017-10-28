package com.virtlink.editorservices.documentation

import com.virtlink.editorservices.codecompletion.ICompletionProposal
import com.virtlink.editorservices.symbols.ISymbol
import java.net.URI

/**
 * Provides the documentation for a symbol.
 */
interface IDocumentationProviderService {

    /**
     * Gets the documentation for the specified symbol.
     *
     * @param symbol The symbol.
     * @return The documentation; or null when there is no documentation.
     */
    fun getDocumentation(symbol: ISymbol): IDocumentationInfo?

    /**
     * Gets the documentation for the specified completion proposal.
     *
     * @param document The URI of the document for which the proposal was created.
     * @param proposal The completion proposal.
     * @return The documentation; or null when there is no documentation.
     */
    fun getDocumentation(document: URI, proposal: ICompletionProposal): IDocumentationInfo?
}