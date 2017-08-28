package org.metaborg.paplj.psi.impl

import com.intellij.psi.tree.IElementType
import org.metaborg.paplj.psi.PapljLiteral

abstract class PapljTextLiteralImplBase(type: IElementType, text: CharSequence) : PapljLiteral.Text(type, text) {



}