package com.virtlink.editorservices.vfs

import java.io.InputStream

/**
 * The content of a file.
 */
interface IFileContent: AutoCloseable {

    /**
     * Gets the file whose content this is.
     */
    val file: IFile

    /**
     * Gets the size of the file, in bytes.
     */
    val size: Long

    /**
     * Gets the last modification stamp of the file.
     */
    val lastModificationStamp: Long

    /**
     * Gets whether the content has any open streams.
     */
    val isOpen: Boolean

    /**
     * Opens an input stream from which the byte content of the file can be read.
     */
    fun getInputStream(): InputStream

    /**
     * Closes all resources used by the content, including any open streams.
     */
    override fun close()

}