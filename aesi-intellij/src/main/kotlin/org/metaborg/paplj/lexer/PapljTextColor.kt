package org.metaborg.paplj.lexer

import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.options.colors.AttributesDescriptor
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors as Default

enum class PapljTextColor(val description: String, val id: String, fallback: TextAttributesKey) {

    // @formatter:off
    IDENTIFIER          ("Identifier", "org.metaborg.paplj.IDENTIFIER", Default.IDENTIFIER),
    KEYWORD             ("Keyword", "org.metaborg.paplj.KEYWORD", Default.KEYWORD);
    // @formatter:on

    val textAttributesKey = TextAttributesKey.createTextAttributesKey(id, fallback)
    val attributesDescriptor = AttributesDescriptor(description, textAttributesKey)

}