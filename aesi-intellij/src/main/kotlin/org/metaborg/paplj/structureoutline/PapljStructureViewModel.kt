package org.metaborg.paplj.structureoutline

import com.intellij.icons.AllIcons
import com.intellij.ide.structureView.StructureViewTreeElement
import com.intellij.ide.structureView.TextEditorBasedStructureViewModel
import com.intellij.ide.structureView.impl.common.PsiTreeElementBase
import com.intellij.openapi.editor.Editor
import com.intellij.psi.PsiElement
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.util.PlatformIcons
import org.metaborg.paplj.psi.PapljFile
import com.virtlink.editorservices.*
import com.virtlink.editorservices.structureoutline.*
import org.metaborg.paplj.PapljIcons
import org.metaborg.paplj.psi.PapljCompositeElement
import org.metaborg.paplj.toOffset
import javax.swing.Icon

class PapljStructureViewModel(editor: Editor?,
                              file: PapljFile,
                              val outliner: IStructureOutliner,
                              val project: IProject,
                              val document: IDocument)
    : TextEditorBasedStructureViewModel(editor, file) {

    override fun getRoot(): StructureViewTreeElement {
        return createTreeElement(null)
    }

    override fun getPsiFile(): PapljFile = super.getPsiFile() as PapljFile

    private inline fun <reified T: PsiElement> findElementAt(offset: Int): T? {
        return PsiTreeUtil.getParentOfType(this.psiFile.findElementAt(offset), T::class.java)
    }

    private fun getChildSymbols(symbol: ISymbol?): Collection<ISymbol> {
        return this.outliner.outline(this.project, this.document, symbol, null)
    }

    private fun createTreeElements(symbols: Collection<ISymbol>): MutableCollection<StructureViewTreeElement> {
        return symbols.map { this.createTreeElement(it) }.toMutableList()
    }

    private fun createTreeElement(symbol: ISymbol?): StructureViewTreeElement {
        val offset = symbol?.location?.toOffset(psiFile)
        val element = (if (offset != null) findElementAt<PapljCompositeElement>(offset) else null) ?: this.psiFile
        return PapljTreeElement(element, symbol, this)
    }

    class PapljTreeElement<T : PsiElement>(element: T, val symbol: ISymbol?, val model: PapljStructureViewModel) : PsiTreeElementBase<T>(element) {

        override fun getIcon(open: Boolean): Icon? {
            return when (symbol?.kind ?: SymbolKind.Constant) {
                SymbolKind.File -> PapljIcons.FILE
                SymbolKind.Module -> PlatformIcons.OPENED_MODULE_GROUP_ICON
                SymbolKind.Namespace -> PlatformIcons.PACKAGE_ICON
                SymbolKind.Package -> PlatformIcons.PACKAGE_ICON
                SymbolKind.Class -> PlatformIcons.CLASS_ICON
                SymbolKind.Interface -> PlatformIcons.INTERFACE_ICON
                SymbolKind.Enum -> PlatformIcons.ENUM_ICON
                SymbolKind.Property -> PlatformIcons.PROPERTY_ICON
                SymbolKind.Field -> PlatformIcons.FIELD_ICON
                SymbolKind.Function -> PlatformIcons.FUNCTION_ICON
                SymbolKind.Method -> PlatformIcons.METHOD_ICON
                SymbolKind.Constructor -> PlatformIcons.METHOD_ICON
                SymbolKind.Variable -> PlatformIcons.VARIABLE_ICON
                SymbolKind.Constant -> PlatformIcons.VARIABLE_READ_ACCESS
            }
        }

        override fun getPresentableText(): String? {
            return symbol?.name ?: "root"
        }

        override fun getChildrenBase(): MutableCollection<StructureViewTreeElement> {
            return this.model.createTreeElements(this.model.getChildSymbols(this.symbol))
        }

    }

}