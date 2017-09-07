package org.metaborg.paplj.syntaxhighlighting

import com.intellij.psi.tree.IElementType
import org.metaborg.paplj.AesiLanguage

/**
 *
 * @property scope: The style scope associated with this token type.
 */
class AesiTokenType(val scope: String) : IElementType(scope, AesiLanguage) {

    override fun toString(): String {
        return AesiTokenType::class.java.name + "." + super.toString() + "::" + this.scope
    }

}