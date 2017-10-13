package com.virtlink.editorservices.documents

import com.virtlink.editorservices.IDocument
import java.io.File

class DefaultDocumentContentFactory : IDocumentContentFactory {

    // FIXME: Character set is probably not always UTF8,
    // but we have no way of knowing the correct character set.
    // In any case, this should be the same character set as used
    // by the editor, as otherwise the character to offset conversions
    // and line-ending detections would not work correctly.
    // So UTF-8 is our best guess here.
    private val defaultCharset = Charsets.UTF_8

    override fun create(document: IDocument, updatable: Boolean): IDocumentContent {
        if (updatable) {
            // NOTE: Will throw an IllegalArgumentException when the
            // document's URI is not a local file.
            val file = File(document.uri)
            // TODO: Determine the character set (used by the editor).
            return LocalDocumentContent(file, defaultCharset)
        } else {
            return RemoteDocumentContent()
        }
    }

}