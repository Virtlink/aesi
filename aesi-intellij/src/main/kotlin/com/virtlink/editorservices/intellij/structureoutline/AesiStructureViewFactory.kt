package com.virtlink.editorservices.intellij.structureoutline

import com.intellij.ide.structureView.StructureViewBuilder
import com.intellij.ide.structureView.StructureViewModel
import com.intellij.ide.structureView.TreeBasedStructureViewBuilder
import com.intellij.lang.PsiStructureViewFactory
import com.intellij.openapi.editor.Editor
import com.intellij.psi.PsiFile
import com.virtlink.editorservices.intellij.DocumentManager
import com.virtlink.editorservices.intellij.ProjectManager
import com.virtlink.editorservices.structureoutline.IStructureOutlineService

abstract class AesiStructureViewFactory(
        private var structureOutliner: IStructureOutlineService,
        private var projectManager: ProjectManager,
        private var documentManager: DocumentManager
) : PsiStructureViewFactory {

    override fun getStructureViewBuilder(psiFile: PsiFile): StructureViewBuilder? {
        val project = this.projectManager.getProjectForFile(psiFile)
        val document = this.documentManager.getDocument(psiFile)

        return object : TreeBasedStructureViewBuilder() {
            override fun createStructureViewModel(editor: Editor?): StructureViewModel {
                return AesiStructureViewModel(editor, psiFile, structureOutliner, project, document)
            }

            override fun isRootNodeShown(): Boolean = false
        }
    }
}