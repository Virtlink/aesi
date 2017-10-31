package com.virtlink.editorservices.vfs2

import com.virtlink.editorservices.vfs.IFile
import java.io.InputStream
import java.io.Reader
import java.nio.charset.Charset

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
     * Gets whether the content is textual.
     */
    val isText: Boolean

    /**
     * Gets the encoding of the content;
     * or null when the content is binary.
     */
    val encoding: Charset?

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
     * Opens a reader from which the character content of the document can be read.
     *
     * Will fail when the content is binary.
     */
    fun getReader(): Reader

    /**
     * Closes all resources used by the content, including any open streams.
     */
    override fun close()

}