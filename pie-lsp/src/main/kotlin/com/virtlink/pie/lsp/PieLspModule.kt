package com.virtlink.pie.lsp

import com.google.inject.Binder
import com.virtlink.editorservices.lsp.AesiModule
import com.virtlink.pie.PieImplModule
import mb.log.LogModule
import mb.vfs.VFSModule
import org.slf4j.LoggerFactory

class PieLspModule : PieImplModule() {

    override fun configure(binder: Binder) {
        super.configure(binder)
        binder.install(AesiModule())
        binder.install(VFSModule())
        binder.install(LogModule(LoggerFactory.getLogger("root")))
    }
}