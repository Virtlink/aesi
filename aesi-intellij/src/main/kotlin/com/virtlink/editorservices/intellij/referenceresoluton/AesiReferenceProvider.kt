package com.virtlink.editorservices.intellij.referenceresoluton

import com.google.inject.Inject
import com.intellij.openapi.diagnostic.Logger
import com.intellij.psi.*
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.util.ProcessingContext
import com.virtlink.editorservices.intellij.psi.AesiPsiElement
import com.virtlink.editorservices.intellij.psi.AesiPsiRootElement
import com.virtlink.editorservices.intellij.resources.IntellijResourceManager
import com.virtlink.editorservices.referenceresolution.IDefinition
import com.virtlink.editorservices.referenceresolution.IReferenceResolverService

class AesiReferenceProvider @Inject constructor(
        private val referenceResolver: IReferenceResolverService,
        private val resourceManager: IntellijResourceManager)
    : PsiReferenceProvider() {

    private val LOG = Logger.getInstance(AesiReferenceProvider::class.java)

    override fun getReferencesByElement(element: PsiElement, context: ProcessingContext): Array<PsiReference> {
        if (element !is AesiPsiElement || element is AesiPsiRootElement)
            return PsiReference.EMPTY_ARRAY

        val documentUri = this.resourceManager.getUri(element.containingFile)
        val offset = element.textOffset.toLong()
        val resolutionInfo = referenceResolver.resolve(documentUri, offset, null)

        if (resolutionInfo == null || resolutionInfo.definitions.isEmpty())
            return PsiReference.EMPTY_ARRAY

        val references = resolutionInfo.definitions.mapNotNull { toResolveResult(it) }.toTypedArray()

        return arrayOf(AesiPsiReference(references, element))//, resolutionInfo.referenceRange?.toTextRange() ?: element.textRange))
    }

    private fun toResolveResult(definition: IDefinition): ResolveResult? {
        val resourceUri = definition.symbol.resource

        if (resourceUri == null) {
            LOG.warn("No resource specified for definition.")
            return null
        }

        val file = this.resourceManager.getPsiFile(resourceUri)
        if (file == null) {
            LOG.warn("$resourceUri: Could not determine PsiFile.")
            return null
        }

        val offset = definition.symbol.nameRange?.startOffset
        if (offset == null) {
            LOG.warn("$definition: No offset specified.")
            return null
        }

        val lexerElement = file.findElementAt(offset.toInt())
        val namedElement = PsiTreeUtil.getParentOfType(lexerElement, AesiPsiElement::class.java, false)
//        val namedElement = PsiTreeUtil.getParentOfType(lexerElement, AesiPsiNamedElement::class.java, false)
        if (namedElement == null) {
            LOG.warn("$file: Could not find PsiNamedElement at $offset.")
            return null
        }

        return PsiElementResolveResult(namedElement)
    }
}