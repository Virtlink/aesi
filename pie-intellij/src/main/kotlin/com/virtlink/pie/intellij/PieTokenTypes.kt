package com.virtlink.pie.intellij

import com.intellij.lang.ASTFactory
import com.intellij.lang.ASTNode
import com.intellij.psi.tree.IElementType
import com.intellij.psi.tree.IFileElementType

object PieTokenTypes {
    val PAPLJ_FILE: IElementType = object : IFileElementType("PAPLJ_FILE", PieLanguage) {
        override fun parseContents(chameleon: ASTNode): ASTNode {
            return ASTFactory.leaf(PAPLJ_TEXT, chameleon.chars)
        }
    }

    val PAPLJ_TEXT = IElementType("PAPLJ_TEXT", PieLanguage)

}