package com.virtlink.editorservices.referenceresolution

import com.virtlink.editorservices.IDocument
import com.virtlink.editorservices.IProject
import com.virtlink.editorservices.Span

/**
 * A reference.
 */
@Deprecated("Replaced")
interface IReference {
    /**
     * The project with the reference.
     */
    val project: IProject
    /**
     * The document with the reference.
     */
    val document: IDocument
    /**
     * The range of the reference's name in the document.
     */
    val range: Span
}