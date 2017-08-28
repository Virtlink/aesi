package org.metaborg.paplj.syntaxhighlighting

import com.intellij.lexer.Lexer
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase
import com.intellij.psi.tree.IElementType
import com.intellij.openapi.editor.HighlighterColors
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors
import com.intellij.openapi.editor.colors.TextAttributesKey.createTextAttributesKey


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
            createScopeStyle("text",                HighlighterColors.TEXT),
            createScopeStyle("source",              HighlighterColors.TEXT),
            createScopeStyle("comment",             DefaultLanguageHighlighterColors.LINE_COMMENT),
            createScopeStyle("constant",            DefaultLanguageHighlighterColors.CONSTANT),
            createScopeStyle("entity",              DefaultLanguageHighlighterColors.CLASS_NAME),
            createScopeStyle("keyword.operator",    DefaultLanguageHighlighterColors.OPERATION_SIGN),
            createScopeStyle("keyword",             DefaultLanguageHighlighterColors.KEYWORD),
            createScopeStyle("storage",             DefaultLanguageHighlighterColors.KEYWORD),
            createScopeStyle("support",             DefaultLanguageHighlighterColors.IDENTIFIER),
            createScopeStyle("variable.language",   DefaultLanguageHighlighterColors.KEYWORD),
            createScopeStyle("variable",            DefaultLanguageHighlighterColors.IDENTIFIER),
            createScopeStyle("string",              DefaultLanguageHighlighterColors.STRING)
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