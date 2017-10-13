package com.virtlink.editorservices.referenceresolution

@Deprecated("Replaced")
class ReferenceResolutionInfo(
//        override val referenceRange: Span?,
        override val definitions: List<IDefinition>)
    : IReferenceResolutionInfo