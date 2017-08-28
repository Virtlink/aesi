package org.metaborg.paplj.lexer

import com.intellij.lexer.Lexer
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors
import com.intellij.openapi.editor.HighlighterColors
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.editor.colors.TextAttributesKey.*
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase
import com.intellij.psi.TokenType
import com.intellij.psi.tree.IElementType
import org.metaborg.paplj.lexer.PapljLexerAdapter
import org.metaborg.paplj.psi.PapljKeywordTokenType
import org.metaborg.paplj.psi.PapljTokenElementTypes as ElementType

class PapljSyntaxHighlighter : SyntaxHighlighterBase() {

    companion object {
        // @formatter:off
        val SEPARATOR       = createTextAttributesKey("PAPLJ_SEPARATOR", DefaultLanguageHighlighterColors.OPERATION_SIGN)
        val KEY             = createTextAttributesKey("PAPLJ_KEY", DefaultLanguageHighlighterColors.KEYWORD)
        val VALUE           = createTextAttributesKey("PAPLJ_VALUE", DefaultLanguageHighlighterColors.STRING)
//        val COMMENT         = createTextAttributesKey("PAPLJ_COMMENT", DefaultLanguageHighlighterColors.LINE_COMMENT)
//        val BAD_CHARACTER   = createTextAttributesKey("PAPLJ_BAD_CHARACTER", HighlighterColors.BAD_CHARACTER)

//        val BAD_CHAR_KEYS   = arrayOf(BAD_CHARACTER)
//        val SEPARATOR_KEYS  = arrayOf(SEPARATOR)
//        val KEY_KEYS        = arrayOf(KEY)
//        val VALUE_KEYS      = arrayOf(VALUE)
//        val COMMENT_KEYS    = arrayOf(COMMENT)
//        val EMPTY_KEYS      = emptyArray<TextAttributesKey>()
        // @formatter:on

        fun toColor(tokenType: IElementType?): PapljTextColor? = when(tokenType) {
            is PapljKeywordTokenType        -> PapljTextColor.KEYWORD

            ElementType.ID                  -> PapljTextColor.IDENTIFIER

            else                            -> null
        }
    }

    override fun getHighlightingLexer(): Lexer {
        return PapljLexerAdapter()
    }

    override fun getTokenHighlights(tokenType: IElementType?): Array<out TextAttributesKey>
            = pack(toColor(tokenType)?.textAttributesKey)
//        return when (tokenType) {
////            PapljTypes.SEPARATOR    -> SEPARATOR_KEYS
////            PapljTypes.KEY          -> KEY_KEYS
////            PapljTypes.VALUE        -> VALUE_KEYS
////            PapljTypes.COMMENT      -> COMMENT_KEYS
//            TokenType.BAD_CHARACTER -> BAD_CHAR_KEYS
//            else                    -> EMPTY_KEYS
//        }


}