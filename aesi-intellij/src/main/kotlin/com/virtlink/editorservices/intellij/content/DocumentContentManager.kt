package com.virtlink.editorservices.intellij.content

import com.google.inject.Inject
import com.virtlink.editorservices.content.IContent
import com.virtlink.editorservices.content.IContentManager
import com.virtlink.editorservices.content.IContentSource
import com.virtlink.editorservices.intellij.IntellijDocument
import com.virtlink.editorservices.intellij.TextDocument
import com.virtlink.logging.logger

/**
 * Manages the content of the documents.
 */
class DocumentContentManager @Inject constructor(
        private val intellijContentSource: IntellijContentSource,
        private val textContentSource: TextContentSource)
    : IContentManager {

    @Suppress("PrivatePropertyName")
    private val LOG by logger()

    override fun getLatestContent(document: IDocument): IContent
            = getContentSource(document).getLatest(document)

    /**
     * Gets the content source of the specified document.
     *
     * @param document The document.
     * @return The content source.
     */
    private fun getContentSource(document: IDocument): IContentSource {
        return when (document) {
            is IntellijDocument -> this.intellijContentSource
            is TextDocument -> this.textContentSource
            else -> throw RuntimeException("Unknown document type: " + document::class.java.name)
        }
    }

}