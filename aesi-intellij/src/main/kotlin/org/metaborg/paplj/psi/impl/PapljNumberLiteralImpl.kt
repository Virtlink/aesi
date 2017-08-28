package org.metaborg.paplj.psi.impl

import com.intellij.psi.tree.IElementType
import org.metaborg.paplj.psi.PapljLiteral

class PapljNumberLiteralImpl(type: IElementType, text: CharSequence) : PapljLiteral.Number(type, text) {

    override fun toString(): String = "PapljNumberLiteralImpl($elementType)"

}