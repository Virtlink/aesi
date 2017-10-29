package com.virtlink.editorservices.intellij.structureoutline

import com.intellij.ide.structureView.StructureViewBuilder
import com.intellij.ide.structureView.StructureViewModel
import com.intellij.ide.structureView.TreeBasedStructureViewBuilder
import com.intellij.lang.PsiStructureViewFactory
import com.intellij.openapi.editor.Editor
import com.intellij.psi.PsiFile

abstract class AesiStructureViewFactory(
        private var structureViewModelFactory: AesiStructureViewModel.IFactory
) : PsiStructureViewFactory {

    override fun getStructureViewBuilder(psiFile: PsiFile): StructureViewBuilder? {
        return object : TreeBasedStructureViewBuilder() {
            override fun createStructureViewModel(editor: Editor?): StructureViewModel {
                return structureViewModelFactory.create(editor, psiFile)
            }

            override fun isRootNodeShown(): Boolean = false
        }
    }
}