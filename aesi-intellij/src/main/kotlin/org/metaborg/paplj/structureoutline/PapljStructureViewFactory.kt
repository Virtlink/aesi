package org.metaborg.paplj.structureoutline

import com.intellij.ide.structureView.StructureViewBuilder
import com.intellij.ide.structureView.StructureViewModel
import com.intellij.ide.structureView.TreeBasedStructureViewBuilder
import com.intellij.lang.PsiStructureViewFactory
import com.intellij.openapi.editor.Editor
import com.intellij.psi.PsiFile
import com.virtlink.editorservices.IDocument
import com.virtlink.editorservices.IProject
import org.metaborg.paplj.PapljDocumentManager
import org.metaborg.paplj.PapljProjectManager
import org.metaborg.paplj.codecompletion.PapljCodeCompleter
import org.metaborg.paplj.psi.PapljFile

class PapljStructureViewFactory : PsiStructureViewFactory {

    // TODO: Inject
    val structureOutliner = PapljStructureOutliner()

    override fun getStructureViewBuilder(psiFile: PsiFile?): StructureViewBuilder? {
        val papljFile = psiFile as PapljFile
        val project: IProject = PapljProjectManager.toAesiProject(psiFile.project)
        val document: IDocument = PapljDocumentManager.toAesiDocument(psiFile)

        return object : TreeBasedStructureViewBuilder() {
            override fun createStructureViewModel(editor: Editor?): StructureViewModel {
                return PapljStructureViewModel(editor, papljFile, structureOutliner, project, document)
            }

            override fun isRootNodeShown(): Boolean = false
        }
    }
}

