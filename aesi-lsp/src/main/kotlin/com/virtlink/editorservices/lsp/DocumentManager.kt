package com.virtlink.editorservices.lsp

import com.virtlink.editorservices.IDocument
import org.eclipse.lsp4j.TextDocumentIdentifier
import java.io.File
import java.net.URI
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap

/**
 * Manages the documents.
 */
class DocumentManager {

    private val documentMap = ConcurrentHashMap<URI, VirtualDocument>()

    fun getDocument(uri: URI): ILspDocument {
        return documentMap.computeIfAbsent(uri, {
            val document = VirtualDocument(it)
            // FIXME: Character set is probably not always UTF8,
            // but we have no way of knowing the correct character set.
            // In any case, this should be the same character set as used
            // by the editor, as otherwise the character to offset conversions
            // and line-ending detections would not work correctly.
            // So UTF8 is our best guess here.
            val fullText = File(uri).readText(Charsets.UTF_8)
            document.update(0, 0, fullText)

            document
        })
    }

    fun openDocument(uri: URI, text: String?) {
        TODO()
    }

    fun closeDocument(uri: URI) {
        TODO()
    }
}