package com.virtlink.editorservices.intellij.syntaxcoloring

import com.intellij.lexer.Lexer
import com.virtlink.editorservices.IDocument
import com.virtlink.editorservices.IProject

/**
 * Factory for the language-specific lexer.
 */
interface ILexerFactory {

    /**
     * Creates the lexer for the given project and document.
     *
     * @param project The project.
     * @param document The document.
     */
    fun create(project: IProject, document: IDocument): Lexer
}