package com.virtlink.editorservices.lsp

import com.virtlink.editorservices.ICancellationToken
import org.eclipse.lsp4j.jsonrpc.CancelChecker
import java.util.concurrent.CancellationException

/**
 * Converts a CancelChecker object into a cancellation token.
 */
fun CancelChecker.toCancellationToken(): ICancellationToken
    = LspCancellationToken(this)

private class LspCancellationToken(private val checker: CancelChecker): ICancellationToken {
    override val isCancelled: Boolean
        get() = try {
                    checker.checkCanceled()
                    false
                } catch (_: CancellationException) {
                    true
                }
}