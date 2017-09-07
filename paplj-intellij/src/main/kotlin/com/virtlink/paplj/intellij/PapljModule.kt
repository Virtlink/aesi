package com.virtlink.paplj.intellij

import com.virtlink.editorservices.intellij.AesiModule
import com.virtlink.editorservices.codecompletion.ICodeCompleter
import com.virtlink.paplj.DummyCodeCompleter


class PapljModule : AesiModule() {
    override fun bindCodeCompletion() {
        bind(ICodeCompleter::class.java).to(DummyCodeCompleter::class.java)
        super.bindCodeCompletion()
    }
}