package com.virtlink.editorservices.intellij.psi

import com.google.inject.Inject
import com.intellij.lang.Language

/**
 * Tracks element types.
 *
 * For token types, see [AesiTokenTypeManager].
 */
class AesiElementTypeManager
@Inject constructor(language: Language)  {

    val contentElementType = AesiElementType(language)

}