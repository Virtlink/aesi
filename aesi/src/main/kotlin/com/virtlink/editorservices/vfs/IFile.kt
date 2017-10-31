package com.virtlink.editorservices.vfs

import com.virtlink.editorservices.resources.IContent

/**
 * A file.
 */
interface IFile: IResource {

    /**
     * Gets the current content of the file.
     */
    val content: IFileContent

}