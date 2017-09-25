package com.virtlink.editorservices.intellij

import com.intellij.openapi.editor.Document
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.fileEditor.FileDocumentManager
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiDocumentManager
import com.intellij.psi.PsiFile
import com.virtlink.editorservices.IDocument
import com.virtlink.editorservices.IProject

class DocumentManager {
    fun getDocument(document: Document): IDocument {
        return IntellijDocument(document)
    }
    fun getDocument(editor: Editor): IDocument {
        return getDocument(editor.document)
    }
    fun getDocument(file: PsiFile): IDocument {
        val document = PsiDocumentManager.getInstance(file.project).getDocument(file)
        return if (document != null) getDocument(document) else TextDocument(file.text)
    }
    fun getDocument(file: VirtualFile): IDocument {
        return getDocument(FileDocumentManager.getInstance().getDocument(file)!!)
    }

    fun getPsiFile(project: IProject, document: IDocument): PsiFile? {
        if (document !is IntellijDocument || project !is IntellijProject)
            return null

        return PsiDocumentManager.getInstance(project.intellijModule.project).getPsiFile(document.intellijDocument)
    }
}