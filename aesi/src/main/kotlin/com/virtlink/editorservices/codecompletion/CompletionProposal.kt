package com.virtlink.editorservices.codecompletion

import com.virtlink.editorservices.Kind
import com.virtlink.editorservices.IVisibility
import java.util.*

class CompletionProposal(override val label: String,
                         override val description: String? = null,
                         override val documentation: String? = null,
                         override val caseSensitive: Boolean = true,
                         override val insertionText: String? = null,
                         override val caret: Int? = null,
                         override val postfix: String? = null,
                         override val type: String? = null,
                         override val priority: Int = 0,
                         override val kind: Kind = Kind.Unknown,
                         override val visibility: IVisibility? = null,
                         override val attributes: EnumSet<Attribute> = EnumSet.noneOf(Attribute::class.java)) : ICompletionProposal