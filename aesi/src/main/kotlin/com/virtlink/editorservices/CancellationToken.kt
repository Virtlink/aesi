package com.virtlink.editorservices

@Deprecated("Removed")
class CancellationToken : ICancellationToken {
    override var cancelled: Boolean = false
        private set

    fun cancel() {
        this.cancelled = true
    }
}