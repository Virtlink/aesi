package com.virtlink.editorservices.symbols

import com.virtlink.editorservices.ICancellationToken
import com.virtlink.editorservices.IDocument
import com.virtlink.editorservices.IProject
import com.virtlink.editorservices.Offset
import com.virtlink.editorservices.referenceresolution.IDefinitionResolutionInfo

/**
 * Provides the definition(s) of the symbol under the caret.
 */
interface IDefinitionProviderService {

    /**
     * Returns the definitions for the reference at the current caret position in the text in a document.
     *
     * The cancellation token is used to abort a long-running operation when the result is no longer needed.
     * Implementations should listen to the cancellation event and abort, for example by throwing an
     * exception or returning a dummy result. It is allowed to ignore the cancellation event, but
     * this may negatively impact performance.
     *
     * @param document The document for which to provide definition resolution.
     * @param caretOffset The offset of the caret in the document.
     * @param cancellationToken The cancellation token; or `null` when not supported.
     * @return A list of definitions.
     */
    fun getDefinitions(
            document: IDocument,
            caretOffset: Offset,
            cancellationToken: ICancellationToken?)
            : List<ISymbol>
}