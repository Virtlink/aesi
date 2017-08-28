package org.metaborg.paplj

import com.intellij.openapi.fileTypes.LanguageFileType
import javax.swing.Icon

object PapljFileType : LanguageFileType(PapljLanguage) {

    const val EXTENSION = "pj"

    override fun getName(): String {
        return "PAPLJ"
    }

    override fun getDefaultExtension(): String {
        return EXTENSION
    }

    override fun getDescription(): String {
        return "PAPLJ file"
    }

    override fun getIcon(): Icon? {
        return PapljIcons.FILE
    }

}
