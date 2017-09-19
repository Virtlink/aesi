package com.virtlink.editorservices.intellij

import com.intellij.openapi.module.Module
import com.intellij.openapi.project.Project
import com.intellij.openapi.roots.ProjectRootManager
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiFile
import com.virtlink.editorservices.IProject

class ProjectManager {
    fun getProject(module: Module): IProject {
        return IntellijProject(module)
    }
    fun getProjectForFile(project: Project, virtualFile: VirtualFile): IProject {
        return getProject(ProjectRootManager.getInstance(project).fileIndex.getModuleForFile(virtualFile)!!)
    }
    fun getProjectForFile(file: PsiFile): IProject {
        return getProjectForFile(file.project, file.virtualFile)
    }
}