package com.virtlink.editorservices.intellij.content

import com.intellij.openapi.editor.Document
import com.intellij.openapi.editor.EditorFactory
import com.intellij.openapi.editor.event.*
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.vfs.VirtualFile
import com.virtlink.editorservices.IDocument
import com.virtlink.editorservices.content.IContent
import com.virtlink.editorservices.content.IContentSource
import com.virtlink.editorservices.content.IContentSource2
import com.virtlink.editorservices.content.StringContent
import com.virtlink.editorservices.intellij.DocumentManager
import com.virtlink.editorservices.intellij.IntellijDocument

class IntellijContentSource : IContentSource {

    init {
        EditorFactory.getInstance().eventMulticaster.addDocumentListener(object: DocumentListener {
            override fun documentChanged(event: DocumentEvent) {
                documentVersions.compute(event.document, { d, v ->
                    (v ?: 0) + 1
                })
            }
        })
    }

    private val documentVersions: MutableMap<Document, Int> = mutableMapOf()

    override fun getLatest(document: IDocument): IContent {
        return StringContent((document as IntellijDocument).intellijDocument.text)
    }

}