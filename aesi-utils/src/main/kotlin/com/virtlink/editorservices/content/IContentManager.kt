package com.virtlink.editorservices.content

import com.virtlink.editorservices.IDocument

/**
 * A content manager.
 */
interface IContentManager {
    /**
     * Gets the latest content of the specified document.
     *
     * @param document The document.
     * @return The latest content.
     */
    fun getLatestContent(document: IDocument): IContent
}