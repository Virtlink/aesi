package com.virtlink.editorservices.intellij

import com.intellij.openapi.module.Module
import com.intellij.openapi.project.Project
import com.intellij.openapi.roots.ProjectRootManager
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiFile
import com.intellij.testFramework.LightVirtualFile

class ProjectManager {
    fun getProject(module: Module?): IProject {
        if (module == null)
            return DummyProject
        return IntellijProject(module)
    }

    fun getProjectForFile(project: Project?, virtualFile: VirtualFile?): IProject {
        var module: Module? = null

        if (project != null && virtualFile != null) {
            module = ProjectRootManager.getInstance(project).fileIndex.getModuleForFile(virtualFile)
            if (module == null && virtualFile is LightVirtualFile && virtualFile.originalFile != null) {
                module = ProjectRootManager.getInstance(project).fileIndex.getModuleForFile(virtualFile.originalFile)
            }
        }

        return getProject(module)
    }

    fun getProjectForFile(file: PsiFile?): IProject {
        return getProjectForFile(file?.project, file?.virtualFile)
    }
}