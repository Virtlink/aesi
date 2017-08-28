package org.metaborg.paplj.psi

import com.intellij.psi.impl.source.tree.LeafPsiElement
import com.intellij.psi.tree.IElementType

sealed class PapljLiteral(type: IElementType, text: CharSequence) : LeafPsiElement(type, text) {

    abstract override fun toString(): String

    abstract class Number(type: IElementType, text: CharSequence) : PapljLiteral(type, text)

    abstract class Text(type: IElementType, text: CharSequence) : PapljLiteral(type, text)

}