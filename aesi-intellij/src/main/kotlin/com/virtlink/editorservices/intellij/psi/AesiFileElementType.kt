package com.virtlink.editorservices.intellij.psi

import com.google.inject.Inject
import com.google.inject.assistedinject.Assisted
import com.intellij.lang.ASTNode
import com.intellij.lang.Language
import com.intellij.lang.LanguageParserDefinitions
import com.intellij.lang.PsiBuilderFactory
import com.intellij.psi.PsiElement
import com.intellij.psi.tree.IElementType
import com.intellij.psi.tree.IFileElementType
import com.virtlink.editorservices.intellij.DocumentManager
import com.virtlink.editorservices.intellij.ProjectManager
import com.virtlink.editorservices.intellij.syntaxcoloring.AesiLexer
import com.virtlink.editorservices.syntaxcoloring.ISyntaxColorer
import org.metaborg.paplj.AesiLanguage

class AesiFileElementType @Inject constructor(
        language: Language,
        private val colorizer: ISyntaxColorer,
        private val elementTypeManager: ElementTypeManager,
        private val projectManager: ProjectManager,
        private val documentManager: DocumentManager)
    : IFileElementType(language) {

    override fun doParseContents(chameleon: ASTNode, psi: PsiElement): ASTNode {
        val aesiProject = this.projectManager.getProjectForFile(psi.containingFile)
        val aesiDocument = this.documentManager.getDocument(psi.containingFile)
        val lexer = AesiLexer(aesiProject, aesiDocument, this.elementTypeManager, this.colorizer)
        val builder = PsiBuilderFactory.getInstance().createBuilder(psi.project, chameleon, lexer, language, chameleon.chars)
        val astBuilder = AesiAstBuilder(this.elementTypeManager)
        val tree = astBuilder.build(this, builder)
        return tree.firstChildNode
    }

}