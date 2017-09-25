package com.virtlink.editorservices.referenceresolution

import com.virtlink.editorservices.IDocument
import com.virtlink.editorservices.IProject
import com.virtlink.editorservices.Span

class Reference(
        override val project: IProject,
        override val document: IDocument,
        override val range: Span)
    : IReference