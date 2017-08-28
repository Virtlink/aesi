package com.virtlink.editorservices.codecompletion

import com.virtlink.editorservices.Kind
import com.virtlink.editorservices.IVisibility
import java.util.*

interface ICompletionProposal {
    val name: String
    val caseSensitive: Boolean
    val insertionText: String?
    val caret: Int?
    val postfix: String?
    val type: String?
    val description: String?
    val documentation: String?
    val priority: Int
    val kind: Kind
    val visibility: IVisibility?
    val attributes: EnumSet<Attribute>
}

