package com.virtlink.paplj.lsp

import com.google.inject.AbstractModule
import com.virtlink.editorservices.lsp.AesiLspModule
import com.virtlink.paplj.PapljModule

class PapljLspModule : AbstractModule() {

    override fun configure() {
        install(PapljModule())
        install(AesiLspModule())
    }
}