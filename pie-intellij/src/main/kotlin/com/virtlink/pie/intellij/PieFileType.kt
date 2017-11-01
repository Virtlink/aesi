package com.virtlink.pie.intellij

import com.intellij.openapi.fileTypes.LanguageFileType
import javax.swing.Icon

object PieFileType : LanguageFileType(PieLanguage) {

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
        return PieIcons.FILE
    }

}
