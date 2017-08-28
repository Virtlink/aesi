package org.metaborg.paplj.psi

import com.intellij.psi.tree.IElementType
import org.metaborg.paplj.psi.PapljTokenType

object PapljTokenElementTypes {

    // @formatter:off
    @JvmField val SHA               : IElementType = PapljTokenType("#")
    @JvmField val EXCL              : IElementType = PapljTokenType("!")
    @JvmField val SEMICOLON         : IElementType = PapljTokenType(";")
    @JvmField val DOT               : IElementType = PapljTokenType(".")
    @JvmField val ASTERISK          : IElementType = PapljTokenType("*")
    @JvmField val PLUS              : IElementType = PapljTokenType("+")
    @JvmField val MINUS             : IElementType = PapljTokenType("-")
    @JvmField val SLASH             : IElementType = PapljTokenType("/")
    @JvmField val BRACE_L           : IElementType = PapljTokenType("(")
    @JvmField val BRACE_R           : IElementType = PapljTokenType(")")
    @JvmField val CBRACE_L          : IElementType = PapljTokenType("{")
    @JvmField val CBRACE_R          : IElementType = PapljTokenType("}")
    @JvmField val BRACK_L           : IElementType = PapljTokenType("[")
    @JvmField val BRACK_R           : IElementType = PapljTokenType("]")
    @JvmField val ABRACK_L          : IElementType = PapljTokenType("<")
    @JvmField val ABRACK_R          : IElementType = PapljTokenType(">")
    @JvmField val COMMA             : IElementType = PapljTokenType(",")
    @JvmField val EQ                : IElementType = PapljTokenType("=")
    @JvmField val OROR              : IElementType = PapljTokenType("||")
    @JvmField val ANDAND            : IElementType = PapljTokenType("&&")
    @JvmField val EQEQ              : IElementType = PapljTokenType("==")
    @JvmField val EXCLEQ            : IElementType = PapljTokenType("!=")

    @JvmField val K_PUBLIC          : IElementType = PapljKeywordTokenType("public")
    @JvmField val K_IMPORT          : IElementType = PapljKeywordTokenType("import")
    @JvmField val K_PROGRAM         : IElementType = PapljKeywordTokenType("program")
    @JvmField val K_CLASS           : IElementType = PapljKeywordTokenType("class")
    @JvmField val K_EXTENDS         : IElementType = PapljKeywordTokenType("extends")
    @JvmField val K_RUN             : IElementType = PapljKeywordTokenType("run")
    @JvmField val K_TRUE            : IElementType = PapljKeywordTokenType("true")
    @JvmField val K_FALSE           : IElementType = PapljKeywordTokenType("false")
    @JvmField val K_NEW             : IElementType = PapljKeywordTokenType("new")
    @JvmField val K_NULL            : IElementType = PapljKeywordTokenType("null")
    @JvmField val K_THIS            : IElementType = PapljKeywordTokenType("this")
    @JvmField val K_IF              : IElementType = PapljKeywordTokenType("if")
    @JvmField val K_ELSE            : IElementType = PapljKeywordTokenType("else")
    @JvmField val K_LET             : IElementType = PapljKeywordTokenType("let")
    @JvmField val K_IN              : IElementType = PapljKeywordTokenType("in")
    @JvmField val K_AS              : IElementType = PapljKeywordTokenType("as")

    @JvmField val ID                : IElementType = PapljTokenType("ID")
    @JvmField val INT               : IElementType = PapljLiteralTokenType.createNumberType("<INT>")
    // @formatter:on

}