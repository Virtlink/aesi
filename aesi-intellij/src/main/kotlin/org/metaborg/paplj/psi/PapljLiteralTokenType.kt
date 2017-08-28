package org.metaborg.paplj.psi

import com.intellij.lang.ASTNode
import com.intellij.psi.tree.IElementType
import org.metaborg.paplj.psi.impl.PapljNumberLiteralImpl
import org.metaborg.paplj.psi.impl.PapljStringLiteralImpl

class PapljLiteralTokenType(debugName: String,
                            private val implConstructor: (IElementType, CharSequence) -> PapljLiteral)
        : PapljTokenType(debugName) {

    override fun createLeafNode(leafText: CharSequence): ASTNode = implConstructor(this, leafText)


    companion object {

        @JvmStatic fun createNumberType(debugName: String): PapljLiteralTokenType =
                PapljLiteralTokenType(debugName, ::PapljNumberLiteralImpl)

        @JvmStatic fun createStringType(debugName: String): PapljLiteralTokenType =
                PapljLiteralTokenType(debugName, ::PapljStringLiteralImpl)
    }
}