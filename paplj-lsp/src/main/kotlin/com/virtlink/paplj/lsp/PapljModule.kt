package com.virtlink.paplj.lsp

import com.virtlink.editorservices.codecompletion.ICodeCompleter
import com.virtlink.editorservices.lsp.AesiModule
import com.virtlink.paplj.codecompletion.DummyCodeCompleter

class PapljModule : AesiModule() {

    override fun configure() {
        super.configure()
        bind(ICodeCompleter::class.java).to(DummyCodeCompleter::class.java)
    }
}