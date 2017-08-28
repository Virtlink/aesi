package org.metaborg.paplj.psi.impl

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiReference
import org.metaborg.paplj.psi.PapljCompositeElement

abstract class PapljCompositeElementImpl(node: ASTNode) : ASTWrapperPsiElement(node), PapljCompositeElement {

    override fun getReference(): PsiReference? = null

}