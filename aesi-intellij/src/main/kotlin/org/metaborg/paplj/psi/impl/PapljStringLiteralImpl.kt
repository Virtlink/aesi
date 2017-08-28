package org.metaborg.paplj.psi.impl

import com.intellij.psi.tree.IElementType
import org.metaborg.paplj.psi.PapljLiteral

class PapljStringLiteralImpl(type: IElementType, text: CharSequence) : PapljTextLiteralImplBase(type, text) {

    override fun toString(): String = "PapljStringLiteralImpl($elementType)"

}