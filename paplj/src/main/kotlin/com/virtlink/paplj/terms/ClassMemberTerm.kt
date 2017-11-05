package com.virtlink.paplj.terms

import com.virtlink.terms.ITerm
import com.virtlink.terms.StringTerm

/**
 * The ClassMemberTerm term.
 */
interface ClassMemberTerm : ITerm {

    val name: StringTerm
    val type: StringTerm

}