package com.virtlink.editorservices.intellij

import com.intellij.openapi.editor.Document
import com.virtlink.editorservices.IDocument

class IntellijDocument(private val document: Document) : IDocument {

    override val text: String
        get() = document.text
}