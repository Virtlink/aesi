package org.metaborg.paplj.syntaxhighlighting

import com.intellij.lexer.Lexer
import com.intellij.lexer.LexerBase
import com.intellij.lexer.LexerPosition
import com.intellij.openapi.diagnostic.Logger
import com.intellij.psi.PsiFile
import com.intellij.psi.tree.IElementType
import com.virtlink.editorservices.IDocument
import com.virtlink.editorservices.IProject
import com.virtlink.editorservices.Span
import org.metaborg.paplj.PapljDocumentManager
import org.metaborg.paplj.PapljProjectManager
import org.metaborg.paplj.locationFromOffset
import org.metaborg.paplj.psi.PapljFile
import org.metaborg.paplj.toOffset
import com.intellij.openapi.editor.Document
import com.virtlink.editorservices.syntaxcoloring.ISyntaxColorer
import com.virtlink.editorservices.syntaxcoloring.IToken
import java.lang.Integer.min

//val papljFile = psiFile as PapljFile
//val project: IProject = PapljProjectManager.toAesiProject(psiFile.project)
//val document: IDocument = PapljDocumentManager.toAesiDocument(psiFile)

//class AesiLexer(val project: IProject, val document: IDocument, val ideaDocument: Document) : LexerBase() {
//
//    private val log = Logger.getInstance(AesiLexer::class.java)
//
//    // TODO: Inject
//    private val tokenTypeManager: AesiTokenTypeManager = AesiTokenTypeManager()
//
//    private val syntaxHighlighter: ISyntaxColorer = PapljSyntaxHighlighter()
//    private var aesiTokens: List<IToken> = emptyList()
//    private var bufferStart: Int = 0
//    private var bufferEnd: Int = 0
//    private var buffer: CharSequence? = null
//    private var currentAesiTokenIndex: Int = 0
//    private var currentTokenStart: Int = 0
//    private var currentTokenEnd: Int = 0
//    private var currentTokenType: IElementType? = null
//
//    override fun start(buffer: CharSequence, startOffset: Int, endOffset: Int, initialState: Int) {
//
//        log.debug("Started lexing from $startOffset to $endOffset")
//
//        this.buffer = buffer
//        this.bufferStart = startOffset
//        this.bufferEnd = endOffset
//
////        val start = locationFromOffset(this.ideaDocument, startOffset)
////        val end = locationFromOffset(this.ideaDocument, endOffset)
//        val span = Span(startOffset, endOffset)
//        this.aesiTokens = syntaxHighlighter.highlight(project, document, span, null)
//        this.currentAesiTokenIndex = 0
//        this.currentTokenStart = startOffset
//        this.currentTokenEnd = startOffset
//
//        // Advance to the first token
//        advance()
//    }
//
//    override fun advance() {
//        // The start of the next token is the end of the current token.
//        this.currentTokenStart = this.currentTokenEnd
//
//        if (this.currentTokenStart >= this.bufferEnd) {
//            // We've reached the end of the buffer.
//            this.currentTokenStart = this.bufferEnd
//            this.currentTokenEnd = this.bufferEnd
//            this.currentTokenType = null
//            return
//        }
//
//        // Advance through the Aesi tokens to find the first token whose start is at or after the new offset.
//        advanceAesiTokensUntil(this.currentTokenStart)
//
//        // Try to get the next Aesi token and its offset.
//        val currentAesiToken = tryGetToken(this.currentAesiTokenIndex)
//        val currentAesiTokenOffset = currentAesiToken?.location?.start?.toOffset(this.ideaDocument) ?: this.bufferEnd
//        if (currentAesiTokenOffset > this.currentTokenStart) {
//            // Either there is no new Aesi token, or the new current Aesi token's start offset
//            // is past the current token offset,
//            // so we produce a whitespace token to fill up the gap.
//            this.currentTokenEnd = currentAesiTokenOffset
//            this.currentTokenType = this.tokenTypeManager.defaultTokenType
//
//            log.debug("Advanced to WS $currentTokenType@$currentTokenStart-$currentTokenEnd")
//        } else if (currentAesiToken != null){
//            // The new current Aesi token starts right at the current token offset,
//            // so we produce a corresponding IntelliJ token type to represent it.
//            this.currentTokenEnd = Integer.min(currentAesiToken.location.end.toOffset(this.ideaDocument), this.bufferEnd)
//            this.currentTokenType = convertToTokenType(currentAesiToken)
//
//            log.debug("Advanced to TK $currentTokenType@$currentTokenStart-$currentTokenEnd with Aesi token $currentAesiToken@$currentAesiTokenOffset (${currentAesiToken.location}, ${currentAesiToken.scope})")
//        } else {
//            throw IllegalStateException("We've advanced outside the realm of possibilities.")
//        }
//
//
//    }
//
//    private fun advanceAesiTokensUntil(offset: Int) {
//        while (this.currentAesiTokenIndex >= 0
//                && this.currentAesiTokenIndex < this.aesiTokens.size
//                && this.aesiTokens[this.currentAesiTokenIndex].location.start.toOffset(this.ideaDocument) < offset)
//        {
//            this.currentAesiTokenIndex += 1;
//        }
//        // The `currentTokenOffset` is either out of range,
//        // or the start offset of the `aesiTokens[currentAesiTokenIndex]`
//        // is equal to or greater than the desired offset.
//    }
//
//    override fun getState(): Int {
//        // Unused: always zero.
//        return 0
//    }
//
//    fun convertToTokenType(token: IToken): IElementType {
//        return this.tokenTypeManager.getTokenType(token.scope)
//    }
//
//    fun tryGetToken(index: Int): IToken? {
//        if (index >= 0 && index < this.aesiTokens.size)
//            return this.aesiTokens[index]
//        else
//            return null
//    }
//
//    override fun getTokenStart(): Int {
//        return this.currentTokenStart
//    }
//
//    override fun getTokenType(): IElementType? {
//        return this.currentTokenType
//    }
//
//    override fun getTokenEnd(): Int {
//        return this.currentTokenEnd
//    }
//
//    override fun getBufferEnd(): Int {
//        return this.bufferEnd
//    }
//
//    override fun getBufferSequence(): CharSequence {
//        return this.buffer!!
//    }
//}