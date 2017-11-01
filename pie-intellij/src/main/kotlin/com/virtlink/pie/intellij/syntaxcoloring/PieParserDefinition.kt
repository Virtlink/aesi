package com.virtlink.pie.intellij.syntaxcoloring

import com.intellij.lexer.Lexer
import com.intellij.openapi.project.Project
import com.intellij.psi.tree.IFileElementType
import com.virtlink.editorservices.intellij.psi.AesiTokenTypeManager
import com.virtlink.editorservices.intellij.syntaxcoloring.AesiParserDefinition
import com.virtlink.pie.intellij.PieFileType
import com.virtlink.pie.intellij.PiePlugin

class PieParserDefinition()
    : AesiParserDefinition(PieFileType,
        PiePlugin.default.injector.getInstance(IFileElementType::class.java),
        PiePlugin.default.injector.getInstance(AesiTokenTypeManager::class.java)) {

    override fun createLexer(project: Project?): Lexer {
        return super.createLexer(project)
    }
}