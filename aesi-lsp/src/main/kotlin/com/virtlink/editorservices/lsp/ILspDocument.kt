package com.virtlink.editorservices.lsp

import com.virtlink.editorservices.IDocument
import org.eclipse.lsp4j.Position
import java.io.File
import java.net.URI
import kotlin.concurrent.write

interface ILspDocument : IDocument {

    val project: Project
    val uri: URI

    val relativeUri: URI

    /**
     * Gets the length of the document.
     */
    val length: Int

    /**
     * Gets the offset of the specified line:character within the document.
     *
     * @param line The zero-based line number.
     * @param character The zero-based character offset from the start of the line.
     * @return The zero-based offset from the start of the document;
     * or null when the line or character are out of bounds.
     */
    fun getOffset(line: Int, character: Int): Int?

    /**
     * Gets the line:character of the specified offset within the document.
     *
     * @param offset The zero-based offset from the start of the document.
     * @return The zero-based line number and zero-based character offset from the start of the line,
     * or null when the offset is out of bounds.
     */
    fun getLineCharacterOffset(offset: Int): Position?
}