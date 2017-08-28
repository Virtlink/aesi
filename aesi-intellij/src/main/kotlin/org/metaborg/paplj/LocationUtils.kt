package org.metaborg.paplj

import com.intellij.openapi.editor.Document
import com.intellij.psi.PsiDocumentManager
import com.intellij.psi.PsiFile
import com.virtlink.editorservices.Location

fun locationFromOffset(document: Document, offset: Int): Location {
    val line = document.getLineNumber(offset)
    val character = offset - document.getLineStartOffset(line)
    return Location(line, character)
}

fun locationFromOffset(file: PsiFile, offset: Int): Location? {
    val document = PsiDocumentManager.getInstance(file.project).getDocument(file) ?: return null
    return locationFromOffset(document, offset)
}

fun Location.toOffset(document: Document): Int {
    return document.getLineStartOffset(this.line) + this.character
}

fun Location.toOffset(file: PsiFile): Int? {
    val document = PsiDocumentManager.getInstance(file.project).getDocument(file) ?: return null
    return this.toOffset(document)
}