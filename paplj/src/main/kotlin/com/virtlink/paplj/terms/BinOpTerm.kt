package com.virtlink.paplj.terms

import com.virtlink.terms.ITerm
import com.virtlink.terms.StringTerm

/**
 * The BinOpTerm term.
 */
interface BinOpTerm : ExprTerm {

    val lhs: ExprTerm
    val rhs: ExprTerm

}