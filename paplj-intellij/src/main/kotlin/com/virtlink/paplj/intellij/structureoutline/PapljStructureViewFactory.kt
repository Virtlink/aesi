package com.virtlink.paplj.intellij.structureoutline

import com.virtlink.editorservices.intellij.structureoutline.AesiStructureViewFactory
import com.virtlink.editorservices.intellij.structureoutline.AesiStructureViewModel
import com.virtlink.paplj.intellij.PapljPlugin

class PapljStructureViewFactory : AesiStructureViewFactory(
        PapljPlugin.default.injector.getInstance(AesiStructureViewModel.IFactory::class.java)
)