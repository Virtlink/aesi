package com.virtlink.pie.intellij.syntaxcoloring

import com.virtlink.editorservices.intellij.resources.IntellijResourceManager
import com.virtlink.editorservices.intellij.syntaxcoloring.AesiLexer
import com.virtlink.editorservices.intellij.syntaxcoloring.AesiSyntaxHighlighter
import com.virtlink.editorservices.intellij.syntaxcoloring.AesiSyntaxHighlighterFactory
import com.virtlink.pie.intellij.PiePlugin

class PieSyntaxHighlighterFactory : AesiSyntaxHighlighterFactory(
        PiePlugin.default.injector.getInstance(IntellijResourceManager::class.java),
        PiePlugin.default.injector.getInstance(AesiLexer.IFactory::class.java),
        PiePlugin.default.injector.getInstance(AesiSyntaxHighlighter.IFactory::class.java)
)