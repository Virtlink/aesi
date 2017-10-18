package com.virtlink.editorservices.lsp.documents

import com.virtlink.editorservices.IDocument
import com.virtlink.editorservices.Offset
import com.virtlink.editorservices.Span
import com.virtlink.editorservices.documents.content.DocumentChange
import com.virtlink.editorservices.documents.content.VirtualContentSource

class RemoteContentSource(document: IDocument, version: Int, text: String) : VirtualContentSource(document) {

    /**
     * The latest version of the content.
     */
    private var latestVersion: Int = version

    init {
        update(emptyContent, listOf(DocumentChange(Span(Offset(0), Offset(emptyContent.length)), text)))
    }

    override fun readText(): String {
        TODO("Not natively supported by LSP.")
    }

    /**
     * Applies updates to the specified virtual content.
     *
     * All changes must be specified in terms of the document content being changed.
     * Changes must not overlap, and be specified in the order they occur in the document.
     *
     * @param content The document content being changed.
     * @param changes The changes to apply to the content in parallel.
     * @param newVersion The new version of the content.
     */
    fun update(content: VirtualContent, changes: List<DocumentChange>, newVersion: Int): VirtualContent {
        TODO()
    }
}