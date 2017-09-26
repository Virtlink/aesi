package com.virtlink.editorservices.intellij.referenceresoluton

import com.google.inject.Inject
import com.intellij.openapi.diagnostic.Logger
import com.intellij.psi.*
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.util.ProcessingContext
import com.virtlink.editorservices.intellij.DocumentManager
import com.virtlink.editorservices.intellij.ProjectManager
import com.virtlink.editorservices.intellij.psi.AesiPsiElement
import com.virtlink.editorservices.intellij.psi.AesiPsiNamedElement
import com.virtlink.editorservices.intellij.psi.AesiPsiRootElement
import com.virtlink.editorservices.intellij.toTextRange
import com.virtlink.editorservices.referenceresolution.IDefinition
import com.virtlink.editorservices.referenceresolution.IReferenceResolver

class AesiReferenceProvider @Inject constructor(
        private val referenceResolver: IReferenceResolver,
        private val projectManager: ProjectManager,
        private val documentManager: DocumentManager)
    : PsiReferenceProvider() {

    private val LOG = Logger.getInstance(AesiReferenceProvider::class.java)

    override fun getReferencesByElement(element: PsiElement, context: ProcessingContext): Array<PsiReference> {
        if (element !is AesiPsiElement || element is AesiPsiRootElement)
            return PsiReference.EMPTY_ARRAY

        val project = this.projectManager.getProjectForFile(element.containingFile)
        val document = this.documentManager.getDocument(element.containingFile)
        val offset = element.textOffset
        val resolutionInfo = referenceResolver.resolve(project, document, offset, null)

        if (resolutionInfo.definitions.isEmpty())
            return PsiReference.EMPTY_ARRAY

        val references = resolutionInfo.definitions.mapNotNull { toResolveResult(it) }.toTypedArray()

        return arrayOf(AesiPsiReference(references, element))//, resolutionInfo.referenceRange?.toTextRange() ?: element.textRange))
    }

    private fun toResolveResult(definition: IDefinition): ResolveResult? {
        val file = this.documentManager.getPsiFile(definition.project, definition.document)
        if (file == null) {
            LOG.info("Could not determine PsiFile for document ${definition.document} in project ${definition.project}.")
            return null
        }

        val lexerElement = file.findElementAt(definition.range.startOffset)
        val namedElement = PsiTreeUtil.getParentOfType(lexerElement, AesiPsiElement::class.java, false)
//        val namedElement = PsiTreeUtil.getParentOfType(lexerElement, AesiPsiNamedElement::class.java, false)
        if (namedElement == null) {
            LOG.info("Could not find PsiNamedElement at offset ${definition.range.startOffset} in file $file.")
            return null
        }

        return PsiElementResolveResult(namedElement)
    }
}