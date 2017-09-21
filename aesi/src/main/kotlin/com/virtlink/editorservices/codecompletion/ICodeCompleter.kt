package com.virtlink.editorservices.codecompletion

import com.virtlink.editorservices.ICancellationToken
import com.virtlink.editorservices.IDocument
import com.virtlink.editorservices.IDocumentLocation
import com.virtlink.editorservices.IProject

interface ICodeCompleter {
    /**
     * Returns completion proposals for the current caret position in the text in a document.
     *
     * The completion proposals must be returned in the order they must be displayed to the user.
     * This function should filter the returned proposals by the prefix that the user has typed.
     *
     * The cancellation token is used to abort a long-running operation when the result is no longer needed.
     * Implementations should listen to the cancellation event and abort, for example by throwing an
     * exception or returning a dummy result. It is allowed to ignore the cancellation event, but
     * this may negatively impact performance.
     *
     * @param project The project that contains the document.
     * @param document The document for which to provide completions.
     * @param caretOffset The offset of the caret in the document.
     * @param cancellationToken The cancellation token; or `null` when not supported.
     * @return The completion info.
     */
    fun complete(
            project: IProject,
            document: IDocument,
            caretOffset: Int,
            cancellationToken: ICancellationToken?): ICompletionInfo
}