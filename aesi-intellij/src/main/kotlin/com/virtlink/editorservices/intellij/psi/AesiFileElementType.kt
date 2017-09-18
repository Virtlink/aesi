package com.virtlink.editorservices.intellij.psi

import com.intellij.lang.ASTNode
import com.intellij.lang.Language
import com.intellij.lang.PsiBuilderFactory
import com.intellij.psi.PsiElement
import com.intellij.psi.tree.IFileElementType

class AesiFileElementType(language: Language) : IFileElementType(language) {
    override fun doParseContents(chameleon: ASTNode, psi: PsiElement): ASTNode {
        val builder = PsiBuilderFactory.getInstance().createBuilder(psi.project, chameleon, null, this.language, chameleon.chars)

        val text = builder.originalText.toString()

//        val astBuilder = AstBuilder(languageImpl, this.elementTypeProviderFactory, this.tokenTypesManager)
//        val tree = astBuilder.build(parseResult, this, builder)
//
//        val rootNode = tree.getFirstChildNode()
//
//        val file = psi as MetaborgFile
//        file.putUserData(MetaborgFile.PARSE_RESULT_KEY, parseResult)
//
//        return rootNode

        throw NotImplementedError()
    }
}

