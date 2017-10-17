package com.virtlink.editorservices.intellij

import com.intellij.openapi.editor.Document
import com.virtlink.editorservices.IDocument
import java.net.URI

class IntellijDocument(override val uri: URI, override val version: Int, val intellijDocument: Document) : IDocument {

    val text: String
        get() = intellijDocument.text
}