package com.virtlink.editorservices.intellij.referenceresoluton

import com.intellij.psi.PsiReferenceContributor
import com.intellij.psi.PsiReferenceRegistrar

import com.intellij.patterns.PlatformPatterns.psiElement
import com.virtlink.editorservices.intellij.psi.AesiPsiElement

abstract class AesiReferenceContributor(
        private val referenceProvider: AesiReferenceProvider)
    : PsiReferenceContributor() {

    override fun registerReferenceProviders(registrar: PsiReferenceRegistrar) {
        registrar.registerReferenceProvider(psiElement(AesiPsiElement::class.java), referenceProvider)
    }
}