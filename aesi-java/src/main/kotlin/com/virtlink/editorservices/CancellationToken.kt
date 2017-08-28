package com.virtlink.editorservices

class CancellationToken : ICancellationToken {
    override var cancelled: Boolean = false
        private set

    fun cancel() {
        this.cancelled = true
    }
}