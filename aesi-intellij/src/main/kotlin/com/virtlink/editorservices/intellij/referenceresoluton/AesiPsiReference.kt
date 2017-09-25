package com.virtlink.editorservices.intellij.referenceresoluton

import com.intellij.openapi.util.TextRange
import com.intellij.psi.*
import com.intellij.util.IncorrectOperationException
import com.virtlink.editorservices.intellij.psi.AesiPsiElement

class AesiPsiReference(private val results: Array<ResolveResult>, element: AesiPsiElement, range: TextRange)
    : PsiPolyVariantReferenceBase<AesiPsiElement>(element, range, true) {

    override fun getVariants(): Array<Any> = TODO("Not supported. Apparently deprecated.")

    override fun multiResolve(incompleteCode: Boolean): Array<ResolveResult> = results

}