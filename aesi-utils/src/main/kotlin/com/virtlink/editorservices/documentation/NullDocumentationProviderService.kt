package com.virtlink.editorservices.documentation

import com.virtlink.editorservices.codecompletion.ICompletionProposal
import com.virtlink.editorservices.symbols.ISymbol
import java.net.URI

/**
 * Null implementation for when there is no actual implementation available.
 */
class NullDocumentationProviderService : IDocumentationProviderService {

    override fun getDocumentation(symbol: ISymbol): IDocumentationInfo?
            = null

    override fun getDocumentation(document: URI, proposal: ICompletionProposal): IDocumentationInfo?
            = null
}