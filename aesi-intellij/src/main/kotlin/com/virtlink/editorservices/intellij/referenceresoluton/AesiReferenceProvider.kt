package com.virtlink.editorservices.intellij.referenceresoluton

import com.google.inject.Inject
import com.intellij.openapi.diagnostic.Logger
import com.intellij.psi.*
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.util.ProcessingContext
import com.virtlink.editorservices.Offset
import com.virtlink.editorservices.content.IContentManager
import com.virtlink.editorservices.intellij.DocumentManager
import com.virtlink.editorservices.intellij.ProjectManager
import com.virtlink.editorservices.intellij.psi.AesiPsiElement
import com.virtlink.editorservices.intellij.psi.AesiPsiRootElement
import com.virtlink.editorservices.referenceresolution.IDefinition
import com.virtlink.editorservices.referenceresolution.IReferenceResolverService

class AesiReferenceProvider @Inject constructor(
        private val referenceResolver: IReferenceResolverService,
        private val projectManager: ProjectManager,
        private val documentManager: DocumentManager,
        private val contentManager: IContentManager)
    : PsiReferenceProvider() {

    private val LOG = Logger.getInstance(AesiReferenceProvider::class.java)

    override fun getReferencesByElement(element: PsiElement, context: ProcessingContext): Array<PsiReference> {
        if (element !is AesiPsiElement || element is AesiPsiRootElement)
            return PsiReference.EMPTY_ARRAY

        val project = this.projectManager.getProjectForFile(element.containingFile)
        val document = this.documentManager.getDocument(element.containingFile)
        val offset = Offset(element.textOffset)
        val resolutionInfo = referenceResolver.resolve(project, document, offset, null)

        if (resolutionInfo.definitions.isEmpty())
            return PsiReference.EMPTY_ARRAY

        val references = resolutionInfo.definitions.mapNotNull { toResolveResult(it) }.toTypedArray()

        return arrayOf(AesiPsiReference(references, element))//, resolutionInfo.referenceRange?.toTextRange() ?: element.textRange))
    }

    private fun toResolveResult(definition: IDefinition): ResolveResult? {
        val project = definition.symbol.project
        val document = definition.symbol.document
        if (project == null) {
            LOG.warn("No project specified for definition.")
            return null
        }
        if (document == null) {
            LOG.warn("No document specified for definition in project $project.")
            return null
        }

        val file = this.documentManager.getPsiFile(project, document)
        if (file == null) {
            LOG.warn("Could not determine PsiFile for document $document in project $project.")
            return null
        }

        val offset = definition.symbol.nameRange?.start
        if (offset == null) {
            LOG.warn("No offset specified for definition $definition")
            return null
        }

        val lexerElement = file.findElementAt(offset.value)
        val namedElement = PsiTreeUtil.getParentOfType(lexerElement, AesiPsiElement::class.java, false)
//        val namedElement = PsiTreeUtil.getParentOfType(lexerElement, AesiPsiNamedElement::class.java, false)
        if (namedElement == null) {
            LOG.warn("Could not find PsiNamedElement at value $offset in file $file.")
            return null
        }

        return PsiElementResolveResult(namedElement)
    }
}