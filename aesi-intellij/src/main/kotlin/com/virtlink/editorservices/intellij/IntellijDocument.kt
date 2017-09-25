package com.virtlink.editorservices.intellij

import com.intellij.openapi.editor.Document
import com.virtlink.editorservices.IDocument

class IntellijDocument(val intellijDocument: Document) : IDocument {

    override val text: String
        get() = intellijDocument.text
}