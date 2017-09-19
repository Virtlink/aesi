package com.virtlink.editorservices.intellij.syntaxcoloring

import com.google.inject.Injector
import com.google.inject.Provider
import com.google.inject.assistedinject.Assisted
import com.intellij.codeHighlighting.TextEditorHighlightingPass
import com.intellij.codeHighlighting.TextEditorHighlightingPassFactory
import com.intellij.codeHighlighting.TextEditorHighlightingPassRegistrar
import com.intellij.openapi.components.AbstractProjectComponent
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.fileEditor.FileDocumentManager
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiFile
import com.virtlink.editorservices.intellij.DocumentManager
import com.virtlink.editorservices.intellij.ProjectManager
import com.virtlink.editorservices.syntaxcoloring.ISyntaxColorer

@Suppress("LeakingThis")
abstract class AesiHighlightingPassFactory(
        project: Project,
        registrar: TextEditorHighlightingPassRegistrar)
    : AbstractProjectComponent(project), TextEditorHighlightingPassFactory {

//    private val syntaxColorer: ISyntaxColorer

    init {
        registrar.registerTextEditorHighlightingPass(this, null, null, true, -1)
//        this.syntaxColorer = this.injector.getInstance(ISyntaxColorer::class.java)
    }

    abstract val injector: Injector

    override fun createHighlightingPass(file: PsiFile, editor: Editor): TextEditorHighlightingPass? {
        val vfile = FileDocumentManager.getInstance().getFile(editor.document) ?: return null

        val syntaxColorer = this.injector.getInstance(ISyntaxColorer::class.java)
        val projectManager = this.injector.getInstance(ProjectManager::class.java)
        val documentManager = this.injector.getInstance(DocumentManager::class.java)
        return AesiHighlightingPass(syntaxColorer, projectManager, documentManager, file, editor, 0, editor.document.textLength)
    }
}