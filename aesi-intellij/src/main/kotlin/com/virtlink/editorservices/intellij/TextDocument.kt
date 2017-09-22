package com.virtlink.editorservices.intellij

import com.virtlink.editorservices.IDocument

/**
 * A document whose text is the only known things.
 * It may not be a physical document, or the content
 * may not be stored on disk (yet).
 */
class TextDocument(override val text: String) : IDocument