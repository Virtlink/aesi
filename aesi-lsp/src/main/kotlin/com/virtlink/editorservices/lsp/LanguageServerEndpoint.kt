package com.virtlink.editorservices.lsp

import org.eclipse.lsp4j.jsonrpc.Endpoint
import org.eclipse.lsp4j.jsonrpc.services.GenericEndpoint
import java.util.concurrent.CompletableFuture

@Deprecated("Do not use")
class LanguageServerEndpoint(delegate: Any) : Endpoint {

    private val endpoint: Endpoint

    init {
        if (delegate is Endpoint)
            this.endpoint = delegate
        else
            this.endpoint = GenericEndpoint(delegate)
    }

    override fun notify(method: String, parameter: Any?) {
        this.endpoint.notify(method, parameter)
    }

    override fun request(method: String, parameter: Any?)
            : CompletableFuture<*> {
        return this.endpoint.request(method, parameter)
    }
}