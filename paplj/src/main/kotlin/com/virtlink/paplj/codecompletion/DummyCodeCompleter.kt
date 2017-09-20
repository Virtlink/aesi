package com.virtlink.paplj.codecompletion

import com.virtlink.editorservices.*
import com.virtlink.editorservices.codecompletion.*
import java.util.*

class DummyCodeCompleter : ICodeCompleter {
    override fun complete(document: IDocument, caretOffset: Int, cancellationToken: ICancellationToken?): ICompletionInfo {
        val proposals = listOf(
                CompletionProposal("Hello",
                        insertionText = "hello world!",
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
        // TODO: Determine prefix according to language rules.
        return CompletionInfo("", proposals)
    }
}