package com.virtlink.editorservices.referenceresolution

import com.virtlink.editorservices.Span

/**
 * Resolution information.
 */
@Deprecated("Replaced")
interface IReferenceResolutionInfo {
//    /**
//     * The range of the reference name in the document.
//     */
//    val referenceRange: Span?
    /**
     * The list of definitions of the reference.
     */
    val definitions: List<IDefinition>
}