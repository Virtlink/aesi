package com.virtlink.editorservices.referenceresolution

import com.virtlink.editorservices.Span

/**
 * Resolution information.
 */
interface IReferenceResolutionInfo {

    /**
     * Gets the range of the reference name in the document.
     */
    val referenceRange: Span?
    /**
     * Gets the list of definitions of the reference.
     */
    val definitions: List<IDefinition>

}