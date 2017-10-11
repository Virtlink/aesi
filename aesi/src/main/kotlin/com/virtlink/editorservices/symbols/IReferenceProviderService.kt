package com.virtlink.editorservices.symbols

import com.virtlink.editorservices.ICancellationToken
import com.virtlink.editorservices.IDocument
import com.virtlink.editorservices.IProject

/**
 * Provides the references to the symbol under the caret.
 */
interface IReferenceProviderService {

    /**
     * Returns the references to the definition at the current caret position in the text in a document.
     *
     * The cancellation token is used to abort a long-running operation when the result is no longer needed.
     * Implementations should listen to the cancellation event and abort, for example by throwing an
     * exception or returning a dummy result. It is allowed to ignore the cancellation event, but
     * this may negatively impact performance.
     *
     * @param project The project that contains the document.
     * @param document The document for which to provide definition resolution.
     * @param caretOffset The offset of the caret in the document.
     * @param cancellationToken The cancellation token; or `null` when not supported.
     * @return A list of references.
     */
    fun getReferences(
            project: IProject,
            document: IDocument,
            caretOffset: Int,
            cancellationToken: ICancellationToken?)
            : List<ISymbol>
}