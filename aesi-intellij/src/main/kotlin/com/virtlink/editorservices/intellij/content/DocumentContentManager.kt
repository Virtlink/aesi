package com.virtlink.editorservices.intellij.content

import com.google.inject.Inject
import com.virtlink.editorservices.IDocument
import com.virtlink.editorservices.content.IContent
import com.virtlink.editorservices.content.IContentManager
import com.virtlink.editorservices.content.IContentSource
import com.virtlink.logging.logger
import java.util.concurrent.ConcurrentHashMap

/**
 * Manages the content of the documents.
 */
class DocumentContentManager @Inject constructor(
        private val intellijContentSource: IntellijContentSource)
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
    private fun getContentSource(document: IDocument): IContentSource
            = this.intellijContentSource

}