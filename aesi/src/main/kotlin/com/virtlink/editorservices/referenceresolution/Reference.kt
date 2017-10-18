package com.virtlink.editorservices.referenceresolution

import com.virtlink.editorservices.IDocument
import com.virtlink.editorservices.IProject
import com.virtlink.editorservices.symbols.ISymbol

class Reference(
        override val symbol: ISymbol
) : IDefinition