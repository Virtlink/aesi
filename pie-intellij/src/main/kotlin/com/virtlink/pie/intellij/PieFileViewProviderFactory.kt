package com.virtlink.pie.intellij

import com.intellij.lang.Language
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.FileViewProvider
import com.intellij.psi.PsiManager
import com.virtlink.paplj.intellij.AesiFileViewProviderFactory

class PieFileViewProviderFactory : AesiFileViewProviderFactory() {
    override fun createFileViewProvider(file: VirtualFile, language: Language?, manager: PsiManager, eventSystemEnabled: Boolean)
            : FileViewProvider {
        return PieFileViewProvider(manager, file, eventSystemEnabled, language!!)
    }
}