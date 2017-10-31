package com.virtlink.editorservices.codecompletion

import com.virtlink.editorservices.ICancellationToken
import com.virtlink.editorservices.Offset
import java.net.URI

/**
 * Null implementation for when there is no actual implementation available.
 */
class NullCodeCompletionService: ICodeCompletionService {

    override fun getCompletionInfo(document: URI, caretOffset: Offset, cancellationToken: ICancellationToken): ICompletionInfo?
            = null

}