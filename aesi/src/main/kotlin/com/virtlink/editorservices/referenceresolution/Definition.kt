package com.virtlink.editorservices.referenceresolution

import com.virtlink.editorservices.IDocument
import com.virtlink.editorservices.IProject

@Deprecated("Replaced")
class Definition(
        override val project: IProject,
        override val document: IDocument,
        override val range: Span,
        override val fullName: String)
    : IDefinition