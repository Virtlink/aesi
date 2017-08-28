package org.metaborg.paplj.psi

import com.intellij.lang.ASTNode
import com.intellij.lang.DefaultASTFactory
import com.intellij.openapi.components.service

private val DefaultASTFactory by lazy { service<DefaultASTFactory>() }

class PapljCommentTokenType(debugName: String) : PapljTokenType(debugName) {
    override fun createLeafNode(leafText: CharSequence): ASTNode = DefaultASTFactory.createComment(this, leafText)
}