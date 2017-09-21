package com.virtlink.editorservices.intellij.syntaxcoloring

import com.google.inject.Injector
import com.intellij.openapi.fileTypes.SyntaxHighlighter
import com.intellij.openapi.fileTypes.SyntaxHighlighterFactory
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.virtlink.editorservices.intellij.DocumentManager
import com.virtlink.editorservices.intellij.ProjectManager

abstract class AesiSyntaxHighlighterFactory(injector: Injector)
    : SyntaxHighlighterFactory() {

    private val projectManager = injector.getInstance(ProjectManager::class.java)
    private val documentManager = injector.getInstance(DocumentManager::class.java)
    private val lexerFactory = injector.getInstance(ILexerFactory::class.java)

    override fun getSyntaxHighlighter(project: Project?, virtualFile: VirtualFile?): SyntaxHighlighter {
        val aesiProject = this.projectManager.getProjectForFile(project, virtualFile)
        val aesiDocument = this.documentManager.getDocument(virtualFile!!)
        val lexer = this.lexerFactory.create(aesiProject, aesiDocument)
        return AesiSyntaxHighlighter(lexer)
    }
}