package com.virtlink.pie.intellij.structureoutline

import com.virtlink.editorservices.intellij.structureoutline.AesiStructureViewFactory
import com.virtlink.editorservices.intellij.structureoutline.AesiStructureViewModel
import com.virtlink.pie.intellij.PiePlugin

class PieStructureViewFactory : AesiStructureViewFactory(
        PiePlugin.default.injector.getInstance(AesiStructureViewModel.IFactory::class.java)
)