package org.metaborg.paplj.syntaxhighlighting

import com.intellij.openapi.fileEditor.FileDocumentManager
import com.intellij.openapi.fileTypes.SyntaxHighlighter
import com.intellij.openapi.fileTypes.SyntaxHighlighterFactory
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.virtlink.editorservices.IDocument
import com.virtlink.editorservices.IProject
import org.metaborg.paplj.PapljDocumentManager
import org.metaborg.paplj.PapljProjectManager
import org.metaborg.paplj.psi.PapljFile

class AesiSyntaxHighlighterFactory: SyntaxHighlighterFactory() {
    override fun getSyntaxHighlighter(project: Project?, virtualFile: VirtualFile?): SyntaxHighlighter {
        val ideaDocument = FileDocumentManager.getInstance().getDocument(virtualFile!!)!!
        val aesiProject: IProject = PapljProjectManager.toAesiProject(project!!)
        val aesiDocument: IDocument = PapljDocumentManager.toAesiDocument(virtualFile!!)
        val lexer = AesiLexer(aesiProject, aesiDocument, ideaDocument)
        return AesiSyntaxHighlighter(lexer)
    }
}