package com.virtlink.editorservices.intellij.content

import com.virtlink.editorservices.IDocument
import com.virtlink.editorservices.content.IContent
import com.virtlink.editorservices.content.IContentSource
import com.virtlink.editorservices.content.StringContent
import com.virtlink.editorservices.intellij.TextDocument

class TextContentSource: IContentSource {
    override fun getLatest(document: IDocument): IContent {
        document as TextDocument
        return StringContent(document.text)
    }
}