package com.virtlink.paplj.intellij.codecompletion

import com.virtlink.editorservices.codecompletion.ICodeCompletionService
import com.virtlink.editorservices.intellij.codecompletion.AesiCompletionContributor
import com.virtlink.editorservices.intellij.resources.IntellijResourceManager
import com.virtlink.paplj.intellij.PapljPlugin

class PapljCompletionContributor: AesiCompletionContributor(
        PapljPlugin.getInjector().getInstance(ICodeCompletionService::class.java),
        PapljPlugin.getInjector().getInstance(IntellijResourceManager::class.java)
)