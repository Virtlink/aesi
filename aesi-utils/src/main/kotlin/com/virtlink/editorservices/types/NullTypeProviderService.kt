package com.virtlink.editorservices.types

import com.virtlink.editorservices.ICancellationToken
import com.virtlink.editorservices.codecompletion.ICompletionProposal
import com.virtlink.editorservices.symbols.ISymbol
import java.net.URI

/**
 * Null implementation for when there is no actual implementation available.
 */
class NullTypeProviderService : ITypeProviderService {

    override fun getType(symbol: ISymbol, cancellationToken: ICancellationToken): ISymbol? = null

    override fun getType(document: URI, proposal: ICompletionProposal, cancellationToken: ICancellationToken): ISymbol? = null
}