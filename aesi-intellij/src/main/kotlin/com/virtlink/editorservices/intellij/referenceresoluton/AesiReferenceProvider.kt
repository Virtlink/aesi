package com.virtlink.editorservices.intellij.referenceresoluton

import com.intellij.psi.PsiElement
import com.intellij.psi.PsiReference
import com.intellij.psi.PsiReferenceProvider
import com.intellij.psi.PsiReferenceService
import com.intellij.psi.impl.source.resolve.reference.impl.providers.CustomizableReferenceProvider
import com.intellij.psi.impl.source.resolve.reference.impl.providers.GenericReferenceProvider
import com.intellij.psi.scope.PsiScopeProcessor
import com.intellij.util.ProcessingContext

class AesiReferenceProvider : GenericReferenceProvider(), CustomizableReferenceProvider {
    override fun handleEmptyContext(processor: PsiScopeProcessor?, position: PsiElement?) {
        super.handleEmptyContext(processor, position)
    }

    override fun setOptions(options: MutableMap<CustomizableReferenceProvider.CustomizationKey<Any>, Any>?) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getOptions(): MutableMap<CustomizableReferenceProvider.CustomizationKey<Any>, Any> {
        return HashMap()
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getReferencesByElement(element: PsiElement, context: ProcessingContext): Array<PsiReference> {
        return emptyArray()
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}