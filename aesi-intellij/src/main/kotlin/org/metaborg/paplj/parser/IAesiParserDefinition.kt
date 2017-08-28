package org.metaborg.paplj.parser

import com.intellij.lang.ParserDefinition
import com.intellij.lexer.Lexer
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiFile

interface IAesiParserDefinition : ParserDefinition {
    /**
     * Returns a lexer for lexing the specified file in the specified project.
     * This lexer does not need to support incremental lexing.
     *
     * @param project The project to which the lexer is connected.
     * @param file The PSI file being lexed.
     *
     * @return The lexer.
     */
    fun createLexer(project: Project, file: PsiFile?): Lexer
}