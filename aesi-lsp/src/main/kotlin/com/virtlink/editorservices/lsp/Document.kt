package com.virtlink.editorservices.lsp

import com.virtlink.editorservices.IDocument
import java.io.File
import java.net.URI

class Document(val uri: URI): IDocument {
    override val text: String
        // FIXME: Character set is probably not always UTF8,
        // but we have no way of knowing the correct character set.
        // In any case, this should be the same character set as used
        // by the editor, as otherwise the character to offset conversions
        // and line-ending detections would not work correctly.
        // So UTF8 is our best guess here.
        get() = File(uri).readText(Charsets.UTF_8)
}
