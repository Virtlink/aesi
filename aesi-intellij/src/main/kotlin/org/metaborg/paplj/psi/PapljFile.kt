package org.metaborg.paplj.psi

import com.intellij.extapi.psi.PsiFileBase
import com.intellij.openapi.fileTypes.FileType
import com.intellij.psi.FileViewProvider
import org.metaborg.paplj.AesiFileType
import org.metaborg.paplj.AesiLanguage
import javax.swing.Icon

class PapljFile(viewProvider: FileViewProvider) : PsiFileBase(viewProvider, AesiLanguage) {

    override fun getFileType(): FileType {
        return AesiFileType
    }

    override fun getIcon(flags: Int): Icon? {
        return super.getIcon(flags)
    }

    override fun toString(): String {
        return "PAPLJ File"
    }

}