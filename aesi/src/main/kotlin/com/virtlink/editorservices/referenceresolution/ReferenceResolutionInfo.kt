package com.virtlink.editorservices.referenceresolution

import com.virtlink.editorservices.Span

@Deprecated("Replaced")
class ReferenceResolutionInfo(
//        override val referenceRange: Span?,
        override val definitions: List<IDefinition>)
    : IReferenceResolutionInfo