package com.virtlink.editorservices.referenceresolution

import com.virtlink.editorservices.Span

class ReferenceResolutionInfo(
        override val referenceRange: Span?,
        override val definitions: List<IDefinition>)
    : IReferenceResolutionInfo