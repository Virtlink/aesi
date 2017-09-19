package com.virtlink.paplj.intellij

import com.intellij.lang.Language
import com.intellij.openapi.fileTypes.FileType
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiManager
import com.intellij.psi.SingleRootFileViewProvider

class PapljFileViewProvider(manager: PsiManager, virtualFile: VirtualFile, eventSystemEnabled: Boolean, language: Language)
    : AesiFileViewProvider(manager, virtualFile, eventSystemEnabled, language) {

    override fun createFile(project: Project, file: VirtualFile, fileType: FileType): PsiFile? {
        return PapljFileImpl(this)
    }

    override fun createCopy(copy: VirtualFile): SingleRootFileViewProvider {
        return PapljFileViewProvider(this.manager, copy, false, this.baseLanguage)
    }
}