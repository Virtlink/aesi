package com.virtlink.editorservices.vfs

import com.virtlink.editorservices.resources.IContent

/**
 * A file with textual content.
 */
interface IDocument: IFile {

    /**
     * Gets the current content of the file.
     */
    override val content: IDocumentContent

}