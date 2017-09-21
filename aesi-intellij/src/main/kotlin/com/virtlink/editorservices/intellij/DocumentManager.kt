package com.virtlink.editorservices.intellij

import com.intellij.openapi.editor.Document
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.fileEditor.FileDocumentManager
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiDocumentManager
import com.intellij.psi.PsiFile
import com.virtlink.editorservices.IDocument

class DocumentManager {
    fun getDocument(document: Document): IDocument {
        return IntellijDocument(document)
    }
    fun getDocument(editor: Editor): IDocument {
        return getDocument(editor.document)
    }
    fun getDocument(file: PsiFile): IDocument {
        return getDocument(PsiDocumentManager.getInstance(file.project).getDocument(file)!!)
    }
    fun getDocument(file: VirtualFile): IDocument {
        return getDocument(FileDocumentManager.getInstance().getDocument(file)!!)
    }
}