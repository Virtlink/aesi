package com.virtlink.editorservices.intellij.syntaxcoloring

import com.google.inject.Inject
import com.intellij.lang.ASTNode
import com.intellij.lang.Language
import com.intellij.lang.ParserDefinition
import com.intellij.lang.PsiParser
import com.intellij.lexer.Lexer
import com.intellij.openapi.fileTypes.LanguageFileType
import com.intellij.openapi.project.Project
import com.intellij.psi.FileViewProvider
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.tree.IFileElementType
import com.intellij.psi.tree.TokenSet
import com.virtlink.editorservices.intellij.psi.AesiFile2
import com.virtlink.editorservices.intellij.psi.AesiFileElementType
import com.virtlink.editorservices.intellij.psi.AesiPsiElement
import org.metaborg.paplj.AesiLanguage

open class AesiParserDefinition @Inject constructor(
        private val fileType: LanguageFileType,
        private val fileElementType: IFileElementType)
    : ParserDefinition {

    override fun getFileNodeType(): IFileElementType = this.fileElementType

    override fun spaceExistanceTypeBetweenTokens(left: ASTNode?, right: ASTNode?): ParserDefinition.SpaceRequirements
            = ParserDefinition.SpaceRequirements.MAY

    override fun getStringLiteralElements(): TokenSet = TokenSet.EMPTY

    override fun getWhitespaceTokens(): TokenSet = TokenSet.EMPTY

    override fun getCommentTokens(): TokenSet = TokenSet.EMPTY

    override fun createLexer(project: Project?): Lexer
        = throw UnsupportedOperationException("See AesiFileElementType.doParseContents().")

    override fun createParser(project: Project?): PsiParser
        = throw UnsupportedOperationException("See AesiFileElementType.doParseContents().")

    override fun createFile(viewProvider: FileViewProvider?): PsiFile = AesiFile2(viewProvider!!, this.fileType)

    override fun createElement(node: ASTNode?): PsiElement = AesiPsiElement(node!!)
}