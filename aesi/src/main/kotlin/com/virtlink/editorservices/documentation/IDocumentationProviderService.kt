package com.virtlink.editorservices.documentation

import com.virtlink.editorservices.IDocument
import com.virtlink.editorservices.IProject
import com.virtlink.editorservices.codecompletion.ICompletionProposal
import com.virtlink.editorservices.symbols.ISymbol

/**
 * Provides the documentation for a symbol.
 */
interface IDocumentationProviderService {

    /**
     * Gets the documentation for the specified symbol.
     *
     * @param project The project that contains the symbol.
     * @param symbol The symbol.
     * @return The documentation; or null when there is no documentation.
     */
    fun getDocumentation(project: IProject, symbol: ISymbol): IDocumentationInfo?

    /**
     * Gets the documentation for the specified completion proposal.
     *
     * @param project The project that contains the document.
     * @param document The document for which the proposal was created.
     * @param proposal The completion proposal.
     * @return The documentation; or null when there is no documentation.
     */
    fun getDocumentation(project: IProject, document: IDocument, proposal: ICompletionProposal): IDocumentationInfo?
}