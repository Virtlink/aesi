package com.virtlink.editorservices.intellij.syntaxcoloring

import com.google.inject.Inject
import com.google.inject.assistedinject.Assisted
import com.intellij.lexer.LexerBase
import com.intellij.openapi.diagnostic.Logger
import com.intellij.psi.tree.IElementType
import com.virtlink.editorservices.IDocument
import com.virtlink.editorservices.IProject
import com.virtlink.editorservices.Span
import com.virtlink.editorservices.intellij.psi.AesiTokenTypeManager
import com.virtlink.editorservices.syntaxcoloring.ISyntaxColorer
import com.virtlink.editorservices.syntaxcoloring.IToken

class AesiLexer @Inject constructor(
        @Assisted private val project: IProject,
        @Assisted private val document: IDocument,
        private val tokenTypeManager: AesiTokenTypeManager,
        private val colorer: ISyntaxColorer)
    : LexerBase() {

    private val LOG = Logger.getInstance(this.javaClass)

    private var buffer: CharSequence? = null
    private var startOffset: Int = 0
    private var endOffset: Int = 0
    private var tokens = emptyList<AesiToken>()
    private var tokenIndex: Int = 0

    override fun start(buffer: CharSequence, startOffset: Int, endOffset: Int, initialState: Int) {
        assert(initialState == 0)
        assert(0 <= startOffset && startOffset <= buffer.length)
        assert(0 <= endOffset && endOffset <= buffer.length)

        LOG.debug("Lexing $document in $project...")

        this.buffer = buffer
        this.startOffset = startOffset
        this.endOffset = endOffset
        this.tokenIndex = 0


        if (buffer.isEmpty()) {
            LOG.debug("Buffer is empty.")
            this.tokens = emptyList()
        } else {
            val highlighterTokens = this.colorer.highlight(this.project, this.document, Span(startOffset, endOffset), null)
            LOG.debug("Highlighter returned ${highlighterTokens.size} tokens")
            this.tokens = tokenize(highlighterTokens)
        }
        LOG.debug("Tokenizer produced ${this.tokens.size} tokens")
    }

    private fun tokenize(tokens: List<IToken>): List<AesiToken> {
        val newTokens = mutableListOf<AesiToken>()
        var offset = 0

        for (token in tokens) {
            val tokenStart = token.location.startOffset
            val tokenEnd = token.location.endOffset

            // We assume that tokens are non-empty. When we encounter
            // a token with an end at or before its start,
            // it gets ignored.
            if (tokenEnd <= tokenStart) continue

            // We assume the list of tokens is ordered by offset.
            // When we encounter a token that's before the current
            // `offset`, it gets ignored.
            // We assume that no tokens overlap. When we encounter a
            // token that starts before the previous token ends,
            // it gets ignored.
            if (tokenStart < offset) continue

            // We assume that tokens cover all characters. When we
            // encounter a stretch of characters not covered by a
            // token, we assign it our own dummy token/element.
            if (offset < tokenStart) {
                // Add dummy element.
                offset = addTokenElement(newTokens, null, offset, tokenStart)
            }

            assert(offset == tokenStart)

            // Add element.
            offset = addTokenElement(newTokens, token, offset, tokenEnd)

            // When we've seen tokens up to the end of the highlighted range
            // we bail out.
            if (offset >= this.endOffset)
                break
        }

        // When there is a gap between the last token and the end of the highlighted range
        // we insert our own dummy token/element.
        if (offset < this.endOffset) {
            offset = addTokenElement(newTokens, null, offset, this.endOffset)
        }

        assert(offset >= this.endOffset)

        return newTokens
    }

    private fun addTokenElement(tokenList: MutableList<AesiToken>, token: IToken?, offset: Int, endOffset: Int): Int {
        val tokenType = getTokenType(token)
        tokenList.add(AesiToken(offset, endOffset, tokenType))
        return endOffset
    }

    private fun getTokenType(token: IToken?): IElementType
        = this.tokenTypeManager.getTokenType(token?.scope)

    override fun advance() {
        tokenIndex++
    }

    override fun getTokenStart(): Int {
        assert(0 <= tokenIndex && tokenIndex < tokens.size,
                { "Expected index 0 <= $tokenIndex < ${tokens.size}." })
        return tokens[tokenIndex].startOffset
    }

    override fun getTokenEnd(): Int {
        assert(0 <= tokenIndex && tokenIndex < tokens.size,
                { "Expected index 0 <= $tokenIndex < ${tokens.size}." })
        return tokens[tokenIndex].endOffset
    }

    override fun getTokenType(): IElementType? {
        return if (0 <= tokenIndex && tokenIndex < tokens.size)
            tokens[tokenIndex].tokenType
        else
            null
    }

    override fun getBufferSequence(): CharSequence = this.buffer!!

    override fun getBufferEnd(): Int = this.endOffset

    override fun getState(): Int = 0

    private class AesiToken(
            val startOffset: Int,
            val endOffset: Int,
            val tokenType: IElementType)
}