package com.virtlink.editorservices.intellij.structureoutline

import com.google.inject.Inject
import com.google.inject.assistedinject.Assisted
import com.intellij.ide.structureView.StructureViewTreeElement
import com.intellij.ide.structureView.TextEditorBasedStructureViewModel
import com.intellij.ide.structureView.impl.common.PsiTreeElementBase
import com.intellij.openapi.editor.Editor
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.util.PlatformIcons
import com.virtlink.editorservices.NullCancellationToken
import com.virtlink.editorservices.intellij.psi.AesiPsiElement
import com.virtlink.editorservices.intellij.resources.IntellijResourceManager
import com.virtlink.editorservices.structureoutline.*
import javax.swing.Icon

class AesiStructureViewModel @Inject constructor(
        @Assisted editor: Editor?,
        @Assisted psiFile: PsiFile,
        private val structureOutlineService: IStructureOutlineService,
        private val resourceManager: IntellijResourceManager)
    : TextEditorBasedStructureViewModel(editor, psiFile) {
//    : StructureViewModelBase(psiFile, editor, null), StructureViewModel.ElementInfoProvider {

    interface IFactory {
        fun create(editor: Editor?, psiFile: PsiFile): AesiStructureViewModel
    }

    override fun getRoot(): StructureViewTreeElement {
        return RootElement(this.psiFile, this)
    }

    private inline fun <reified T: PsiElement> findElementAt(offset: Int): T? {
        return PsiTreeUtil.getParentOfType(this.psiFile.findElementAt(offset), T::class.java)
    }

    private fun getRootNodes(): List<IStructureOutlineElement> {
        val documentUri = this.resourceManager.getUri(this.psiFile)
        return this.structureOutlineService.getRoots(documentUri, NullCancellationToken)?.elements ?: emptyList()
    }

    private fun getChildNodes(node: IStructureOutlineElement): List<IStructureOutlineElement> {
        val documentUri = this.resourceManager.getUri(this.psiFile)
        return this.structureOutlineService.getChildren(documentUri, node, NullCancellationToken)?.elements ?: emptyList()
    }

    private fun createTreeElements(symbols: Collection<IStructureOutlineElement>): MutableCollection<StructureViewTreeElement> {
        return symbols.map { this.createTreeElement(it) }.toMutableList()
    }

    private fun createTreeElement(node: IStructureOutlineElement): StructureViewTreeElement {
        val offset = node.nameSpan?.startOffset
        val element = (if (offset != null) findElementAt<AesiPsiElement>(offset.toInt()) else null) ?: this.psiFile
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
            val node: IStructureOutlineElement,
            val model: AesiStructureViewModel)
        : PsiTreeElementBase<T>(element) {

        override fun getIcon(open: Boolean): Icon? {
            return when {
                "meta.file" in node.scopes -> PlatformIcons.FILE_ICON
                "meta.module" in node.scopes -> PlatformIcons.OPENED_MODULE_GROUP_ICON
                "meta.namespace" in node.scopes -> PlatformIcons.PACKAGE_ICON
                "meta.package" in node.scopes -> PlatformIcons.PACKAGE_ICON
                "meta.class" in node.scopes -> PlatformIcons.CLASS_ICON
                "meta.interface" in node.scopes -> PlatformIcons.INTERFACE_ICON
                "meta.enum" in node.scopes -> PlatformIcons.ENUM_ICON
                "meta.property" in node.scopes -> PlatformIcons.PROPERTY_ICON
                "meta.field" in node.scopes -> PlatformIcons.FIELD_ICON
                "meta.function" in node.scopes -> PlatformIcons.FUNCTION_ICON
                "meta.method" in node.scopes -> PlatformIcons.METHOD_ICON
                "meta.constructor" in node.scopes -> PlatformIcons.METHOD_ICON
                "meta.variable" in node.scopes -> PlatformIcons.VARIABLE_ICON
                "meta.constant" in node.scopes -> PlatformIcons.VARIABLE_READ_ACCESS
                else -> PlatformIcons.VARIABLE_READ_ACCESS
            }
        }

        override fun getPresentableText(): String? {
            return node.label   // ?: "root"
        }

        override fun getChildrenBase(): MutableCollection<StructureViewTreeElement> {
            return this.model.createTreeElements(this.model.getChildNodes(this.node))
        }

    }

}