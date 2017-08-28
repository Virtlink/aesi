package org.metaborg.paplj.psi

import com.intellij.extapi.psi.PsiFileBase
import com.intellij.openapi.fileTypes.FileType
import com.intellij.openapi.util.UserDataHolderBase
import com.intellij.psi.FileViewProvider
import org.metaborg.paplj.PapljFileType
import org.metaborg.paplj.PapljLanguage
import javax.swing.Icon

class PapljFile(viewProvider: FileViewProvider) : PsiFileBase(viewProvider, PapljLanguage) {

    override fun getFileType(): FileType {
        return PapljFileType
    }

    override fun getIcon(flags: Int): Icon? {
        return super.getIcon(flags)
    }

    override fun toString(): String {
        return "PAPLJ File"
    }

}