package com.virtlink.paplj.intellij.codecompletion

import com.virtlink.editorservices.codecompletion.ICodeCompletionService
import com.virtlink.editorservices.intellij.DocumentManager
import com.virtlink.editorservices.intellij.ProjectManager
import com.virtlink.editorservices.intellij.codecompletion.AesiCompletionContributor
import com.virtlink.paplj.intellij.PapljPlugin

class PapljCompletionContributor: AesiCompletionContributor(
        PapljPlugin.getInjector().getInstance(ICodeCompletionService::class.java),
        PapljPlugin.getInjector().getInstance(ProjectManager::class.java),
        PapljPlugin.getInjector().getInstance(DocumentManager::class.java)
)