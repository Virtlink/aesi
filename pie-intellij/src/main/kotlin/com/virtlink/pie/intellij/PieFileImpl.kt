package com.virtlink.pie.intellij

import com.intellij.openapi.fileTypes.FileType
import com.intellij.psi.FileViewProvider
import com.virtlink.paplj.intellij.AesiFileImpl

class PieFileImpl(provider: FileViewProvider)
    : AesiFileImpl(PieTokenTypes.PAPLJ_FILE, provider), PieFile {

    override fun getFileType(): FileType {
        return PieFileType
    }
}