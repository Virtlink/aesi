package com.virtlink.editorservices.lsp

import com.virtlink.editorservices.IDocument
import org.eclipse.lsp4j.TextDocumentIdentifier
import java.net.URI

/**
 * Manages the documents.
 */
class DocumentManager {
    fun getDocument(id: TextDocumentIdentifier): IDocument {
        return Document(URI(id.uri))
    }
}