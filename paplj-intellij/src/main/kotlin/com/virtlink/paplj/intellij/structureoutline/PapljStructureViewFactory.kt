package com.virtlink.paplj.intellij.structureoutline

import com.intellij.psi.tree.IFileElementType
import com.virtlink.editorservices.intellij.DocumentManager
import com.virtlink.editorservices.intellij.ProjectManager
import com.virtlink.editorservices.intellij.structureoutline.AesiStructureViewFactory
import com.virtlink.editorservices.structureoutline.IStructureOutlineService
import com.virtlink.paplj.intellij.PapljPlugin

class PapljStructureViewFactory : AesiStructureViewFactory(
        PapljPlugin.getInjector().getInstance(IStructureOutlineService::class.java),
        PapljPlugin.getInjector().getInstance(ProjectManager::class.java),
        PapljPlugin.getInjector().getInstance(DocumentManager::class.java)
)