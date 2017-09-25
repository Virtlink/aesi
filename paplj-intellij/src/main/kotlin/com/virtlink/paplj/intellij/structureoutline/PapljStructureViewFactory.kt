package com.virtlink.paplj.intellij.structureoutline

import com.intellij.psi.tree.IFileElementType
import com.virtlink.editorservices.intellij.DocumentManager
import com.virtlink.editorservices.intellij.ProjectManager
import com.virtlink.editorservices.intellij.structureoutline.AesiStructureViewFactory
import com.virtlink.editorservices.structureoutline.IStructureOutliner
import com.virtlink.paplj.intellij.PapljPlugin

class PapljStructureViewFactory : AesiStructureViewFactory(
        PapljPlugin.getInjector().getInstance(IStructureOutliner::class.java),
        PapljPlugin.getInjector().getInstance(ProjectManager::class.java),
        PapljPlugin.getInjector().getInstance(DocumentManager::class.java)
)