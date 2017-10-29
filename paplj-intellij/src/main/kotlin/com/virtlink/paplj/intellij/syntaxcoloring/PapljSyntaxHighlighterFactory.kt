package com.virtlink.paplj.intellij.syntaxcoloring

import com.virtlink.editorservices.intellij.resources.IntellijResourceManager
import com.virtlink.editorservices.intellij.syntaxcoloring.AesiLexer
import com.virtlink.editorservices.intellij.syntaxcoloring.AesiSyntaxHighlighter
import com.virtlink.editorservices.intellij.syntaxcoloring.AesiSyntaxHighlighterFactory
import com.virtlink.editorservices.intellij.syntaxcoloring.ScopeManager
import com.virtlink.paplj.intellij.PapljPlugin

class PapljSyntaxHighlighterFactory : AesiSyntaxHighlighterFactory(
        PapljPlugin.getInjector().getInstance(IntellijResourceManager::class.java),
        PapljPlugin.getInjector().getInstance(AesiLexer.IFactory::class.java),
        PapljPlugin.getInjector().getInstance(AesiSyntaxHighlighter.IFactory::class.java)
)