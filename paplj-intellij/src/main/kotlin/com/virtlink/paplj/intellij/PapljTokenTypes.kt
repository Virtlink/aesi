package com.virtlink.paplj.intellij

import com.intellij.lang.ASTFactory
import com.intellij.lang.ASTNode
import com.intellij.openapi.fileTypes.PlainTextLanguage
import com.intellij.psi.tree.IElementType
import com.intellij.psi.tree.IFileElementType

object PapljTokenTypes {
    val PAPLJ_FILE: IElementType = object : IFileElementType("PAPLJ_FILE", PapljLanguage) {
        override fun parseContents(chameleon: ASTNode): ASTNode {
            return ASTFactory.leaf(PAPLJ_TEXT, chameleon.chars)
        }
    }

    val PAPLJ_TEXT = IElementType("PAPLJ_TEXT", PapljLanguage)

}