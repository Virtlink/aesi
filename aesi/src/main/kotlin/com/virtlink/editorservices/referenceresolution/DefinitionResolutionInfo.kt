package com.virtlink.editorservices.referenceresolution

import com.virtlink.editorservices.Span

class DefinitionResolutionInfo(
        override val definitionRange: Span?,
        override val references: List<IReference>)
    : IDefinitionResolutionInfo