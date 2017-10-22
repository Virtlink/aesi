package com.virtlink.editorservices.intellij.structureoutline

import com.intellij.ide.structureView.StructureViewTreeElement
import com.intellij.ide.structureView.TextEditorBasedStructureViewModel
import com.intellij.ide.structureView.impl.common.PsiTreeElementBase
import com.intellij.openapi.editor.Editor
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.util.PlatformIcons
import com.virtlink.editorservices.IDocument
import com.virtlink.editorservices.IProject
import com.virtlink.editorservices.intellij.psi.AesiElementType
import com.virtlink.editorservices.intellij.psi.AesiPsiElement
import com.virtlink.editorservices.structureoutline.*
import com.virtlink.editorservices.symbols.ISymbol
import javax.swing.Icon

class AesiStructureViewModel(editor: Editor?,
                             file: PsiFile,
                             val outliner: IStructureOutlineService,
                             val project: IProject,
                             val document: IDocument)
    : TextEditorBasedStructureViewModel(editor, file) {

    override fun getRoot(): StructureViewTreeElement {
        return RootElement(this.psiFile, this)
    }

//    override fun getPsiFile(): PapljFile = super.getPsiFile() as PapljFile

    private inline fun <reified T: PsiElement> findElementAt(offset: Int): T? {
        return PsiTreeUtil.getParentOfType(this.psiFile.findElementAt(offset), T::class.java)
    }

    private fun getRootNodes(): List<IStructureTreeNode> {
        return this.outliner.getRootNodes(this.project, this.document, null)
    }

    private fun getChildNodes(node: IStructureTreeNode): List<IStructureTreeNode> {
        return this.outliner.getChildNodes(this.project, this.document, node, null)
    }

    private fun createTreeElements(symbols: Collection<IStructureTreeNode>): MutableCollection<StructureViewTreeElement> {
        return symbols.map { this.createTreeElement(it) }.toMutableList()
    }

    private fun createTreeElement(node: IStructureTreeNode): StructureViewTreeElement {
        val offset = node.symbol.nameRange?.start
        val element = (if (offset != null) findElementAt<AesiPsiElement>(offset.value) else null) ?: this.psiFile
        return PapljTreeElement(element, node, this)
    }

    class RootElement<T : PsiElement>(
            element: T,
            val model: AesiStructureViewModel)
        : PsiTreeElementBase<T>(element) {

        override fun getIcon(open: Boolean): Icon? = null

        override fun getPresentableText(): String? {
            return "root"
        }

        override fun getChildrenBase(): MutableCollection<StructureViewTreeElement> {
            return this.model.createTreeElements(this.model.getRootNodes())
        }
    }

    class PapljTreeElement<T : PsiElement>(
            element: T,
            val node: IStructureTreeNode,
            val model: AesiStructureViewModel)
        : PsiTreeElementBase<T>(element) {

        override fun getIcon(open: Boolean): Icon? {
            return when (node.symbol.kind) {
                "file" -> PlatformIcons.FILE_ICON
                "module" -> PlatformIcons.OPENED_MODULE_GROUP_ICON
                "namespace" -> PlatformIcons.PACKAGE_ICON
                "package" -> PlatformIcons.PACKAGE_ICON
                "class" -> PlatformIcons.CLASS_ICON
                "interface" -> PlatformIcons.INTERFACE_ICON
                "enum" -> PlatformIcons.ENUM_ICON
                "property" -> PlatformIcons.PROPERTY_ICON
                "field" -> PlatformIcons.FIELD_ICON
                "function" -> PlatformIcons.FUNCTION_ICON
                "method" -> PlatformIcons.METHOD_ICON
                "constructor" -> PlatformIcons.METHOD_ICON
                "variable" -> PlatformIcons.VARIABLE_ICON
                "constant" -> PlatformIcons.VARIABLE_READ_ACCESS
                else -> PlatformIcons.VARIABLE_READ_ACCESS
            }
        }

        override fun getPresentableText(): String? {
            return node.symbol.label ?: "root"
        }

        override fun getChildrenBase(): MutableCollection<StructureViewTreeElement> {
            return this.model.createTreeElements(this.model.getChildNodes(this.node))
        }

    }

}