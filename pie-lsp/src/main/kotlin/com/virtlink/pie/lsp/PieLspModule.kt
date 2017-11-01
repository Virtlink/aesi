package com.virtlink.pie.lsp

import com.google.inject.AbstractModule
import com.virtlink.editorservices.lsp.AesiLspModule
import com.virtlink.pie.PieModule

class PieLspModule : AbstractModule() {

    override fun configure() {
        install(AesiLspModule())
        install(PieModule())
    }
}