package org.metaborg.paplj

import com.intellij.openapi.fileTypes.LanguageFileType
import com.intellij.util.PlatformIcons
import javax.swing.Icon

object AesiFileType : LanguageFileType(AesiLanguage) {

    const val EXTENSION = "aesi"

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
        return PlatformIcons.ABSTRACT_CLASS_ICON
    }

}