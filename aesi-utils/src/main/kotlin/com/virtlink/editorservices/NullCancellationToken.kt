package com.virtlink.editorservices

/**
 * A cancellation token that can't be cancelled.
 *
 * This object is intended to be used when the editor
 * does not provide cancellation support. It allows
 * services to query for cancellation without having to
 * check for null, even if no cancellation support is provided.
 */
object NullCancellationToken: ICancellationToken {
    override val isCancelled: Boolean
        get() = false
}