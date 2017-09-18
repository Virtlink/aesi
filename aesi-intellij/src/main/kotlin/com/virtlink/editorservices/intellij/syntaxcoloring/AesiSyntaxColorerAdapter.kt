package com.virtlink.editorservices.intellij.syntaxcoloring

import com.intellij.openapi.editor.DefaultLanguageHighlighterColors
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.virtlink.editorservices.syntaxcoloring.IToken
import com.intellij.openapi.editor.markup.HighlighterTargetArea
import com.intellij.openapi.editor.markup.HighlighterLayer
import com.intellij.openapi.editor.markup.TextAttributes
import com.intellij.openapi.editor.colors.EditorColorsScheme
import com.intellij.openapi.editor.colors.EditorColorsManager
import com.intellij.openapi.progress.ProgressIndicator
import com.intellij.openapi.util.Key
import com.virtlink.editorservices.Span
import com.virtlink.editorservices.intellij.toCancellationToken
import com.virtlink.editorservices.syntaxcoloring.ISyntaxColorer
import com.intellij.openapi.editor.markup.RangeHighlighter
import java.util.ArrayList
import com.intellij.openapi.editor.markup.MarkupModel




class AesiSyntaxColorerAdapter(private val editor: Editor,
                               private val startOffset: Int,
                               private val endOffset: Int) {

    val KEY = Key.create<Any>("com.virtlink.editorservices.intellij.syntaxcoloring.AesiSyntaxColorerAdapter")

    private val editorColorsManager = EditorColorsManager.getInstance()

    /**
     * Colorizes the specified tokens in the editor.
     */
    fun colorize(tokens: List<IToken>) {
        removeColorization()

        tokens.forEach {
            colorizeToken(it)
        }
    }

    /**
     * Colorizes a token.
     *
     * @param token The token to colorize.
     */
    private fun colorizeToken(token: IToken) {
        val attributeKeys = getTextAttributeKeys(token)
        val scheme = editorColorsManager.getScheme(EditorColorsScheme.DEFAULT_SCHEME_NAME)
        val attributes = mergeTextAttributes(scheme, attributeKeys)
        val highlighter = editor.markupModel.addRangeHighlighter(
                this.startOffset + token.location.startOffset,
                this.startOffset + token.location.endOffset,
                HighlighterLayer.SYNTAX,
                attributes,
                HighlighterTargetArea.EXACT_RANGE)
        // Tag the highlighter with our class by storing any non-null value.
        highlighter.putUserData(KEY, token)
    }

    private fun removeColorization() {
        val markupModel = editor.markupModel
        val toRemove = markupModel.allHighlighters.filter { highlighter -> highlighter.getUserData<Any>(KEY) != null}.toList()
        for (highlighter in toRemove) {
            markupModel.removeHighlighter(highlighter)
        }
    }

    /**
     * Gets the text attribute keys for styling the specified token.
     *
     * @param token The token to style.
     */
    private fun getTextAttributeKeys(token: IToken): Array<TextAttributesKey> {
        // TODO: Implement
        // See also: https://github.com/langwich/jetbrains-plugin/blob/f60b1a27c8613b98f637088df518453c7c887435/src/java/org/antlr/jetbrains/wichplugin/highlight/WichSyntaxHighlighter.java#L107
        return arrayOf(DefaultLanguageHighlighterColors.KEYWORD)
    }

    /**
     * Takes a sequence of text attribute keys and merges their text attributes together.
     */
    private fun mergeTextAttributes(scheme: EditorColorsScheme, attributeKeys: Array<TextAttributesKey>): TextAttributes {
        return attributeKeys.fold(TextAttributes(),
                { attributes, key -> TextAttributes.merge(attributes, scheme.getAttributes(key)) }
        )
    }
}