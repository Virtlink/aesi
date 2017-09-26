package com.virtlink.editorservices.referenceresolution

import com.virtlink.editorservices.IDocument
import com.virtlink.editorservices.IProject
import com.virtlink.editorservices.Span

/**
 * Resolution information.
 */
interface IDefinitionResolutionInfo {
//    /**
//     * The range of the definition name in the document.
//     */
//    val definitionRange: Span?
    /**
     * The list of references to the definition.
     */
    val references: List<IReference>
}