package com.virtlink.editorservices.intellij.psi

import com.intellij.lang.Language
import com.intellij.psi.tree.IElementType

/**
 * Element type for non-tokens.
 */
open class AesiElementType(language: Language, debugName: String? = null)
    : IElementType(debugName ?: "CONTENT", language)