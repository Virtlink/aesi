package com.virtlink.editorservices.referenceresolution

/**
 * Resolution information.
 */
@Deprecated("Replaced")
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