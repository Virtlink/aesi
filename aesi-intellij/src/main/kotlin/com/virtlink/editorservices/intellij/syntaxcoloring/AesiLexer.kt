package com.virtlink.editorservices.intellij.syntaxcoloring

import com.google.inject.Inject
import com.intellij.lexer.Lexer
import com.intellij.lexer.LexerBase
import com.intellij.psi.tree.IElementType
import com.virtlink.editorservices.syntaxcoloring.ISyntaxColorer

class AesiLexer @Inject constructor(private val colorer: ISyntaxColorer) : LexerBase() {

    override fun getState(): Int = 0

    override fun start(buffer: CharSequence, startOffset: Int, endOffset: Int, initialState: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun advance() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getTokenStart(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getTokenEnd(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getTokenType(): IElementType? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getBufferSequence(): CharSequence {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getBufferEnd(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}