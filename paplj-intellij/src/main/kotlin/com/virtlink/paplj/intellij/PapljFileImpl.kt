package com.virtlink.paplj.intellij

import com.intellij.openapi.fileTypes.FileType
import com.intellij.psi.FileViewProvider
import com.intellij.psi.PsiElementVisitor
import com.intellij.psi.impl.source.PsiFileImpl
import javax.swing.Icon

class PapljFileImpl(provider: FileViewProvider)
    : AesiFileImpl(PapljTokenTypes.PAPLJ_FILE, provider), PapljFile {

    override fun getFileType(): FileType {
        return PapljFileType
    }
}