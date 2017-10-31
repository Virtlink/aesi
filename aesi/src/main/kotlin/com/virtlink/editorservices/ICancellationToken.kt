package com.virtlink.editorservices

/**
 * Object that indicates whether the operation has been cancelled.
 *
 * Cancelled operations still need to return, and they may return
 * with either the complete result, an incomplete result, or an error
 * (which may be a cancellation error indicating that the operation
 * has been cancelled and that there is no result).
 */
interface ICancellationToken {

    /**
     * Gets whether the operation has been cancelled.
     */
    val isCancelled: Boolean

}