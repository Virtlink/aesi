package com.virtlink.paplj.intellij.syntaxcoloring

import com.google.inject.Inject
import com.intellij.lexer.Lexer
import com.intellij.openapi.fileTypes.LanguageFileType
import com.intellij.openapi.project.Project
import com.intellij.psi.tree.IFileElementType
import com.virtlink.editorservices.intellij.syntaxcoloring.AesiParserDefinition
import com.virtlink.paplj.intellij.PapljFileType
import com.virtlink.paplj.intellij.PapljLanguage
import com.virtlink.paplj.intellij.PapljPlugin

class PapljParserDefinition()
    : AesiParserDefinition(PapljFileType, PapljPlugin.getInjector().getInstance(IFileElementType::class.java)) {

    override fun createLexer(project: Project?): Lexer {
        return super.createLexer(project)
    }
}