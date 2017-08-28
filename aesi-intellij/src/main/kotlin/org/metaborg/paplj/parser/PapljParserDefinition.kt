package org.metaborg.paplj.parser

import com.intellij.lang.ASTNode
import com.intellij.lang.Language
import com.intellij.lang.ParserDefinition
import com.intellij.lang.PsiParser
import com.intellij.lexer.Lexer
import com.intellij.openapi.project.Project
import com.intellij.psi.FileViewProvider
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.TokenType
import com.intellij.psi.tree.IFileElementType
import com.intellij.psi.tree.TokenSet
import org.metaborg.paplj.PapljLanguage
import org.metaborg.paplj.lexer.PapljLexerAdapter
import org.metaborg.paplj.psi.PapljFile
import org.metaborg.paplj.psi.PapljTypes

class PapljParserDefinition : IAesiParserDefinition {

    companion object {
        val WHITE_SPACES: TokenSet = TokenSet.create(TokenType.WHITE_SPACE)
//        val COMMENTS: TokenSet = TokenSet.create(PapljTypes.COMMENT)
        val FILE: IFileElementType = IFileElementType(Language.findInstance(PapljLanguage.javaClass))
    }

    override fun createLexer(project: Project) : Lexer {
        return createLexer(project, null)
    }

    override fun createLexer(project: Project, file: PsiFile?): Lexer {
        return PapljLexerAdapter()
    }

    override fun createParser(project: Project?): PsiParser? {
        return PapljParser()
    }

    override fun getFileNodeType(): IFileElementType? {
        return FILE
    }

    override fun createFile(viewProvider: FileViewProvider?): PsiFile? {
        return PapljFile(viewProvider!!)
    }

    override fun createElement(node: ASTNode?): PsiElement {
        return PapljTypes.Factory.createElement(node)
    }

    override fun spaceExistanceTypeBetweenTokens(left: ASTNode?, right: ASTNode?): ParserDefinition.SpaceRequirements? {
        return ParserDefinition.SpaceRequirements.MAY
    }

    override fun getStringLiteralElements(): TokenSet {
        return TokenSet.EMPTY
    }

    override fun getWhitespaceTokens(): TokenSet {
        return WHITE_SPACES
    }

    override fun getCommentTokens(): TokenSet {
        return TokenSet.EMPTY
//        return COMMENTS
    }

}