package com.virtlink.pie.intellij.codecompletion

import com.virtlink.editorservices.codecompletion.ICodeCompletionService
import com.virtlink.editorservices.intellij.codecompletion.AesiCompletionContributor
import com.virtlink.editorservices.intellij.resources.IntellijResourceManager
import com.virtlink.pie.intellij.PiePlugin

class PieCompletionContributor : AesiCompletionContributor(
        PiePlugin.default.injector.getInstance(ICodeCompletionService::class.java),
        PiePlugin.default.injector.getInstance(IntellijResourceManager::class.java)
)