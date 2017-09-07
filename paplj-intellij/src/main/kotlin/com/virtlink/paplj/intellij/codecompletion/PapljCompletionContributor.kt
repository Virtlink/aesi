package com.virtlink.paplj.intellij.codecompletion

import com.virtlink.editorservices.codecompletion.ICodeCompleter
import com.virtlink.editorservices.intellij.codecompletion.AesiCompletionContributor
import com.virtlink.paplj.intellij.PapljPlugin

class PapljCompletionContributor: AesiCompletionContributor(
        PapljPlugin.getInjector().getInstance(ICodeCompleter::class.java)
)