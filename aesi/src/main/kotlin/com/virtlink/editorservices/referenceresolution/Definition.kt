package com.virtlink.editorservices.referenceresolution

import com.virtlink.editorservices.IDocument
import com.virtlink.editorservices.IProject
import com.virtlink.editorservices.symbols.ISymbol

class Definition(
        override val symbol: ISymbol
) : IDefinition