package com.virtlink.paplj.intellij.codecompletion

import com.virtlink.editorservices.codecompletion.ICodeCompletionService
import com.virtlink.editorservices.intellij.codecompletion.AesiCompletionContributor
import com.virtlink.editorservices.intellij.resources.IntellijResourceManager
import com.virtlink.paplj.intellij.PapljPlugin

class PapljCompletionContributor: AesiCompletionContributor(
        PapljPlugin.default.injector.getInstance(ICodeCompletionService::class.java),
        PapljPlugin.default.injector.getInstance(IntellijResourceManager::class.java)
)