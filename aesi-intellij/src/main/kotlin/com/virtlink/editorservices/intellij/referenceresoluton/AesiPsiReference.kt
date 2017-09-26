package com.virtlink.editorservices.intellij.referenceresoluton

import com.intellij.openapi.util.TextRange
import com.intellij.psi.*
import com.intellij.util.IncorrectOperationException
import com.virtlink.editorservices.intellij.psi.AesiPsiElement

//class AesiPsiReference(private val results: Array<ResolveResult>, element: AesiPsiElement, range: TextRange)
//    : PsiReferenceBase<AesiPsiElement>(element, range, false) {
//    override fun resolve(): PsiElement?
//        = if (results.isEmpty()) results[0].element else null
//
//
//    override fun getVariants(): Array<Any> = emptyArray()
//
//}
//
class AesiPsiReference(private val results: Array<ResolveResult>, element: AesiPsiElement)//, range: TextRange)
    : PsiReferenceBase.Poly<AesiPsiElement>(element, TextRange(0, element.textLength), true) {
//    : PsiPolyVariantReferenceBase<AesiPsiElement>(element, range, true) {

    override fun getVariants(): Array<Any> = emptyArray()

    override fun multiResolve(incompleteCode: Boolean): Array<ResolveResult> = results

}