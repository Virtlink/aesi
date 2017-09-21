package com.virtlink.paplj.intellij.syntaxcoloring

import com.virtlink.editorservices.intellij.syntaxcoloring.AesiSyntaxHighlighterFactory
import com.virtlink.paplj.intellij.PapljPlugin

class PapljSyntaxHighlighterFactory
    : AesiSyntaxHighlighterFactory(PapljPlugin.getInjector())