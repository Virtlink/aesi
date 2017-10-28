package com.virtlink.editorservices.codecompletion

import com.virtlink.editorservices.ICancellationToken
import com.virtlink.editorservices.Offset
import java.net.URI

/**
 * Code completion service.
 */
interface ICodeCompletionService {

    /**
     * Returns completion info for the current caret position in the text in a document.
     *
     * The cancellation token is used to abort a long-running operation when the result is no longer needed.
     * Implementations SHOULD listen to the cancellation event and abort, for example by throwing an
     * exception or returning a dummy result. It is allowed to ignore the cancellation event, but
     * this may negatively impact performance.
     *
     * @param document The URI of the document for which to provide completions.
     * @param caretOffset The zero-based offset of the caret in the document.
     * @param cancellationToken The cancellation token; or `null` when not supported.
     * @return The completion info.
     */
    fun getCompletionInfo(
            document: URI,
            caretOffset: Offset,
            cancellationToken: ICancellationToken?)
            : ICompletionInfo
}