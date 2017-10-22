package com.virtlink.editorservices.intellij

import com.intellij.openapi.editor.Document
import com.virtlink.editorservices.IDocument
import java.net.URI

class IntellijDocument(val intellijDocument: Document) : IDocument {

    override val uri: URI = URI("null")

    val text: String
        get() = intellijDocument.text
}