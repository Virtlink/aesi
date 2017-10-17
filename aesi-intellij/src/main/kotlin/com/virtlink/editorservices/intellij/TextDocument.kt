package com.virtlink.editorservices.intellij

import com.virtlink.editorservices.IDocument
import java.net.URI

/**
 * A document whose text is the only known things.
 * It may not be a physical document, or the content
 * may not be stored on disk (yet).
 */
@Deprecated("Replaced by Project")
class TextDocument(val text: String) : IDocument {
    override val uri: URI
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
}