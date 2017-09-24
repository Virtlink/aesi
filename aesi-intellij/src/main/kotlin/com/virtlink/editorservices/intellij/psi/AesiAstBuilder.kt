package com.virtlink.editorservices.intellij.psi

import com.google.inject.Inject
import com.intellij.lang.ASTNode
import com.intellij.lang.PsiBuilder
import com.intellij.psi.tree.IElementType

class AesiAstBuilder @Inject constructor(private val elementTypeManager: AesiElementTypeManager) {

    fun build(root: IElementType, builder: PsiBuilder): ASTNode {
        val m = builder.mark()
        // Add sub-root to prevent ASTNode.firstChild from being null.
        val m2 = builder.mark()
        while (!builder.eof()) {
            val m3 = builder.mark()
            builder.advanceLexer()
            m3.done(elementTypeManager.contentElementType)
        }
        m2.done(elementTypeManager.contentElementType)
        m.done(root)

        return builder.treeBuilt
    }
}