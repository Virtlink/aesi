package com.virtlink.editorservices.intellij.psi

import com.google.inject.Inject
import com.intellij.lang.Language

class ElementTypeManager @Inject constructor(private val language: Language) {

    val contentElementType = AesiElementType(language)
    val dummyElementType = AesiElementType(language)

}