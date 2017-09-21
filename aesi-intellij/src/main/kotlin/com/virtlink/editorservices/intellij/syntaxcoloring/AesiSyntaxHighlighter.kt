package com.virtlink.editorservices.intellij.syntaxcoloring

import com.intellij.lexer.Lexer
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase
import com.intellij.psi.tree.IElementType
import com.intellij.openapi.editor.HighlighterColors
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors
import com.intellij.openapi.editor.colors.TextAttributesKey.createTextAttributesKey
import com.virtlink.editorservices.intellij.psi.AesiTokenType


class AesiSyntaxHighlighter(val lexer: Lexer): SyntaxHighlighterBase() {

    companion object {
        fun createScopeStyle(prefix: String, style: TextAttributesKey): Pair<String, Array<TextAttributesKey>> {
            return Pair(prefix, arrayOf(createTextAttributesKey(createScopeName(prefix), style)))
        }

        fun createScopeName(prefix: String): String {
            return "AESI_" + prefix.toUpperCase().replace('.', '_');
        }
    }

    // TODO: Make these static?
    val BAD_CHARACTER = createTextAttributesKey("AESI_BAD_CHARACTER", HighlighterColors.BAD_CHARACTER)

    private val BAD_CHAR_KEYS = arrayOf(BAD_CHARACTER)
    private val EMPTY_KEYS = emptyArray<TextAttributesKey>()

    private val styleScopes = listOf(
            // Put more specific scopes (longer prefixes) before more general scopes (shorter prefixes).
            // See https://manual.macromates.com/en/language_grammars for details about these scopes.
            createScopeStyle("text",                        HighlighterColors.TEXT),
            createScopeStyle("source",                      HighlighterColors.TEXT),
            // Comments
            createScopeStyle("comment.line",                DefaultLanguageHighlighterColors.LINE_COMMENT),
            createScopeStyle("comment.block",               DefaultLanguageHighlighterColors.BLOCK_COMMENT),
            createScopeStyle("comment.block.documentation", DefaultLanguageHighlighterColors.DOC_COMMENT),
            createScopeStyle("comment",                     DefaultLanguageHighlighterColors.BLOCK_COMMENT),
            // Constants
            createScopeStyle("constant.numeric",            DefaultLanguageHighlighterColors.NUMBER),
            createScopeStyle("constant.character.escape",   DefaultLanguageHighlighterColors.VALID_STRING_ESCAPE),
            createScopeStyle("constant.character",          DefaultLanguageHighlighterColors.STRING),
            createScopeStyle("constant",                    DefaultLanguageHighlighterColors.CONSTANT),
            // Entities
            createScopeStyle("entity.name.function",        DefaultLanguageHighlighterColors.FUNCTION_DECLARATION),
            createScopeStyle("entity.name.type",            DefaultLanguageHighlighterColors.CLASS_NAME),
            createScopeStyle("entity.name",                 DefaultLanguageHighlighterColors.CLASS_NAME),
            createScopeStyle("entity.other.inherited-class",DefaultLanguageHighlighterColors.CLASS_NAME),
            createScopeStyle("entity.other.attribute-name", DefaultLanguageHighlighterColors.CLASS_NAME),
            createScopeStyle("entity.other",                DefaultLanguageHighlighterColors.CLASS_NAME),
            // Invalid
            createScopeStyle("invalid.illegal",             BAD_CHARACTER),
            createScopeStyle("invalid.deprecated",          HighlighterColors.TEXT),    // TODO: Strikethrough text
            createScopeStyle("invalid",                     HighlighterColors.TEXT),    // TODO: Red text
            // Keywords
            createScopeStyle("keyword.operator",            DefaultLanguageHighlighterColors.OPERATION_SIGN),
            createScopeStyle("keyword.control",             DefaultLanguageHighlighterColors.KEYWORD),
            createScopeStyle("keyword",                     DefaultLanguageHighlighterColors.KEYWORD),
            // Markup
            createScopeStyle("markup.underline.link",       DefaultLanguageHighlighterColors.KEYWORD),
            createScopeStyle("markup.underline",            DefaultLanguageHighlighterColors.KEYWORD),
            createScopeStyle("markup.bold",                 DefaultLanguageHighlighterColors.KEYWORD),
            createScopeStyle("markup.italic",               DefaultLanguageHighlighterColors.KEYWORD),
            createScopeStyle("markup.list",                 DefaultLanguageHighlighterColors.KEYWORD),
            createScopeStyle("markup.quote",                DefaultLanguageHighlighterColors.KEYWORD),
            createScopeStyle("markup.raw",                  DefaultLanguageHighlighterColors.KEYWORD),
            // Storage
            createScopeStyle("storage.type",                DefaultLanguageHighlighterColors.KEYWORD),
            createScopeStyle("storage.modifier",            DefaultLanguageHighlighterColors.KEYWORD),
            createScopeStyle("storage",                     DefaultLanguageHighlighterColors.KEYWORD),
            // Strings
            createScopeStyle("string.quoted.single",        DefaultLanguageHighlighterColors.STRING),
            createScopeStyle("string.quoted.double",        DefaultLanguageHighlighterColors.STRING),
            createScopeStyle("string.quoted.triple",        DefaultLanguageHighlighterColors.STRING),
            createScopeStyle("string.quoted",               DefaultLanguageHighlighterColors.STRING),
            createScopeStyle("string.unquoted",             DefaultLanguageHighlighterColors.STRING),
            createScopeStyle("string.interpolated",         DefaultLanguageHighlighterColors.STRING),
            createScopeStyle("string.regexp",               DefaultLanguageHighlighterColors.STRING),
            createScopeStyle("string",                      DefaultLanguageHighlighterColors.STRING),
            // Support
            createScopeStyle("support.function",            DefaultLanguageHighlighterColors.PREDEFINED_SYMBOL),
            createScopeStyle("support.class",               DefaultLanguageHighlighterColors.PREDEFINED_SYMBOL),
            createScopeStyle("support.type",                DefaultLanguageHighlighterColors.PREDEFINED_SYMBOL),
            createScopeStyle("support.constant",            DefaultLanguageHighlighterColors.PREDEFINED_SYMBOL),
            createScopeStyle("support.variable",            DefaultLanguageHighlighterColors.PREDEFINED_SYMBOL),
            createScopeStyle("support",                     DefaultLanguageHighlighterColors.PREDEFINED_SYMBOL),
            // Variables
            createScopeStyle("variable.parameter",          DefaultLanguageHighlighterColors.PARAMETER),
            createScopeStyle("variable.language",           DefaultLanguageHighlighterColors.KEYWORD),
            createScopeStyle("variable",                    DefaultLanguageHighlighterColors.IDENTIFIER)
    )

    override fun getTokenHighlights(tokenType: IElementType?): Array<TextAttributesKey> {
        if (tokenType !is AesiTokenType)
            return EMPTY_KEYS

        return this.styleScopes.filter { (prefix, _) -> tokenType.scope.startsWith(prefix, true) }
                .map { (_, style) -> style }
                .firstOrNull() ?: EMPTY_KEYS
    }

    override fun getHighlightingLexer(): Lexer {
        return this.lexer
    }
}