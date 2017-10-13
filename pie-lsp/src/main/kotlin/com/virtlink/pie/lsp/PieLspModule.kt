package com.virtlink.pie.lsp
import com.google.inject.multibindings.MapBinder
import com.virtlink.editorservices.codecompletion.ICodeCompletionService

import com.google.inject.Binder
import com.virtlink.editorservices.lsp.AesiModule
import com.virtlink.pie.PieImplModule

class PieLspModule : PieImplModule() {

    override fun configure(binder: Binder) {
        super.configure(binder)
        binder.install(AesiModule())
    }
}