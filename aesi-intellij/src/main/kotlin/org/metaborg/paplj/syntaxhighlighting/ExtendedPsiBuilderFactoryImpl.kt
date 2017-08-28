package org.metaborg.paplj.syntaxhighlighting

import com.intellij.lang.*
import com.intellij.lang.impl.PsiBuilderFactoryImpl
import com.intellij.lexer.Lexer
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiFile
import com.virtlink.editorservices.Location
import org.metaborg.paplj.parser.IAesiParserDefinition
import org.metaborg.paplj.toOffset

class ExtendedPsiBuilderFactoryImpl: PsiBuilderFactoryImpl() {

    override fun createBuilder(project: Project, chameleon: ASTNode): PsiBuilder {
        return createBuilder(project, chameleon, null, chameleon.elementType.language, chameleon.chars)
    }

    override fun createBuilder(project: Project, chameleon: LighterLazyParseableNode): PsiBuilder {
        return createBuilder(project, chameleon, null, chameleon.tokenType.language, chameleon.text)
    }

    override fun createBuilder(parserDefinition: ParserDefinition, lexer: Lexer, seq: CharSequence): PsiBuilder {
        return super.createBuilder(parserDefinition, lexer, seq)
    }

    override fun createBuilder(project: Project, chameleon: ASTNode, lexer: Lexer?, lang: Language, seq: CharSequence): PsiBuilder {
        val actualLexer = lexer ?: createLexer(lang, project, chameleon.psi.containingFile)
        return super.createBuilder(project, chameleon, actualLexer, lang, seq)
    }

    override fun createBuilder(project: Project, chameleon: LighterLazyParseableNode, lexer: Lexer?, lang: Language, seq: CharSequence): PsiBuilder {
        val actualLexer = lexer ?: createLexer(lang, project, chameleon.containingFile)
        return super.createBuilder(project, chameleon, actualLexer, lang, seq)
    }

    private fun createLexer(lang: Language, project: Project, file: PsiFile?): Lexer {
        val parserDefinition = LanguageParserDefinitions.INSTANCE.forLanguage(lang) ?: error("No ParserDefinition for language: " + lang.id)

        if (parserDefinition is IAesiParserDefinition) {
            return parserDefinition.createLexer(project, file)
        } else {
            return parserDefinition.createLexer(project)
        }
    }

}