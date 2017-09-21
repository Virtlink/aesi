package com.virtlink.editorservices.intellij.referenceresoluton

import com.intellij.psi.PsiReferenceContributor
import com.intellij.psi.PsiReferenceRegistrar

import com.intellij.patterns.PlatformPatterns.psiFile
import com.virtlink.paplj.intellij.AesiFile

abstract class AesiReferenceContributor : PsiReferenceContributor() {
    override fun registerReferenceProviders(registrar: PsiReferenceRegistrar) {
        registrar.registerReferenceProvider(psiFile(AesiFile::class.java), AesiReferenceProvider())
    }
}