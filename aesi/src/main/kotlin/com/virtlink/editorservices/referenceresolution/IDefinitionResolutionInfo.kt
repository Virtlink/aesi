package com.virtlink.editorservices.referenceresolution

import com.virtlink.editorservices.Span
import java.io.Serializable

/**
 * Resolution information.
 */
interface IDefinitionResolutionInfo : Serializable {

    /**
     * Gets the range of the definition name in the document.
     */
    val definitionRange: Span?

    /**
     * Gets the list of references to the definition.
     */
    val references: List<IReference>

}