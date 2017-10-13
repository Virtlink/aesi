package com.virtlink.editorservices.referenceresolution

@Deprecated("Replaced")
class DefinitionResolutionInfo(
//        override val definitionRange: Span?,
        override val references: List<IReference>)
    : IDefinitionResolutionInfo