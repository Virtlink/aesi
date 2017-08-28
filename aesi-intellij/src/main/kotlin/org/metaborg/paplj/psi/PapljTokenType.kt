package org.metaborg.paplj.psi

import com.intellij.lang.ASTNode
import com.intellij.lang.DefaultASTFactory
import com.intellij.openapi.components.service
import com.intellij.psi.impl.source.tree.LeafPsiElement
import com.intellij.psi.tree.IElementType
import com.intellij.psi.tree.ILeafElementType
import org.metaborg.paplj.PapljLanguage

open class PapljTokenType(debugName: String) : IElementType(debugName, PapljLanguage), ILeafElementType {
    override fun createLeafNode(leafText: CharSequence): ASTNode = LeafPsiElement(this, leafText)
}