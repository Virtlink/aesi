package com.virtlink.editorservices.intellij

import com.intellij.openapi.editor.Document
import java.net.URI

class IntellijDocument(val intellijDocument: Document) {

    override val uri: URI = URI("null")

    val text: String
        get() = intellijDocument.text
}