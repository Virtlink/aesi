package org.metaborg.paplj.codecompletion

import com.virtlink.editorservices.*
import com.virtlink.editorservices.codecompletion.Attribute
import com.virtlink.editorservices.codecompletion.CompletionProposal
import com.virtlink.editorservices.codecompletion.ICodeCompleter
import com.virtlink.editorservices.codecompletion.ICompletionProposal
import java.util.*

class PapljCodeCompleter : ICodeCompleter {
    override fun complete(project: IProject, caret: IDocumentLocation, smart: Boolean, cancellationToken: ICancellationToken): List<ICompletionProposal> {
        return listOf(
                CompletionProposal("Hello",
                        insertionText = caret.document.path + ":" + caret.offset,
                        postfix = "(subject: World)",
                        type = "Type",
                        description = "Description string",
                        documentation = "Extensive documentation",
                        kind = Kind.Method,
                        visibility = Visibility(ClassVisibility.Protected, PackageVisibility.Public, LibraryVisibility.Public),
                        attributes = EnumSet.of(Attribute.Static)),
                CompletionProposal("Local variable",
                        insertionText = "local var",
                        type = "String",
                        kind = Kind.Variable,
                        visibility = Visibility(ClassVisibility.Private, PackageVisibility.Public, LibraryVisibility.Public),
                        attributes = EnumSet.of(Attribute.NotInherited)),
                CompletionProposal("Method",
                        insertionText = "method()",
                        caret = 7,
                        type = "String",
                        postfix = " (deprecated)",
                        kind = Kind.AbstractMethod,
                        visibility = Visibility(ClassVisibility.Public, PackageVisibility.Package, LibraryVisibility.Public),
                        attributes = EnumSet.of(Attribute.Deprecated)),
                CompletionProposal("if",
                        postfix = " then else")
        )
    }
}