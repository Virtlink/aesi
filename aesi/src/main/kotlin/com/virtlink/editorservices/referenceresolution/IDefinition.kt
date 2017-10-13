package com.virtlink.editorservices.referenceresolution

import com.virtlink.editorservices.IDocument
import com.virtlink.editorservices.IProject

/**
 * A definition.
 */
@Deprecated("Replaced")
interface IDefinition {
    /**
     * The project with the definition.
     */
    val project: IProject
    /**
     * The document with the definition.
     */
    val document: IDocument
    /**
     * The range of the definition's name in the document.
     */
    val range: Span
    /**
     * The fully qualified name of the definition.
     */
    val fullName: String
}