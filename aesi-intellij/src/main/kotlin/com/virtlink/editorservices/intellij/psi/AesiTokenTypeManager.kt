package com.virtlink.editorservices.intellij.psi

import com.google.inject.Inject
import com.intellij.lang.Language

/**
 * Tracks token types.
 *
 * IntelliJ can't handle too many token types. However, we'll still need a different token type for
 * each different style. This class returns a token type that matches the style, if found; otherwise,
 * creates a new token type and stores it for re-use.

 * The token type manager is specific to a single language.
 */
class AesiTokenTypeManager
@Inject constructor(private val language: Language) {

    val scopedElementTypes = HashMap<String, AesiTokenType>()

    val defaultScope: String get() = "text"

    fun getTokenType(scope: String?): AesiTokenType
        = scopedElementTypes.getOrPut(scope ?: defaultScope, { AesiTokenType(scope ?: defaultScope, language) })

}