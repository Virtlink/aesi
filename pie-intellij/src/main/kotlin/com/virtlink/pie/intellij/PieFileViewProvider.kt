package com.virtlink.pie.intellij

import com.intellij.lang.Language
import com.intellij.openapi.fileTypes.FileType
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiManager
import com.intellij.psi.SingleRootFileViewProvider
import com.virtlink.paplj.intellij.AesiFileViewProvider

class PieFileViewProvider(manager: PsiManager, virtualFile: VirtualFile, eventSystemEnabled: Boolean, language: Language)
    : AesiFileViewProvider(manager, virtualFile, eventSystemEnabled, language) {

    override fun createFile(project: Project, file: VirtualFile, fileType: FileType): PsiFile? {
        return PieFileImpl(this)
    }

    override fun createCopy(copy: VirtualFile): SingleRootFileViewProvider {
        return PieFileViewProvider(this.manager, copy, false, this.baseLanguage)
    }
}