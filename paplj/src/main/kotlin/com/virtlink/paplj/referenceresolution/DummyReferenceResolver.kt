package com.virtlink.paplj.referenceresolution

import com.google.inject.Inject
import com.virtlink.editorservices.*
import com.virtlink.editorservices.content.IContentManager
import com.virtlink.editorservices.referenceresolution.*
import com.virtlink.editorservices.symbols.Symbol

class DummyReferenceResolver @Inject constructor(private val contentManager: IContentManager) : IReferenceResolverService {
    override fun resolve(project: IProject, document: IDocument, caretOffset: Offset, cancellationToken: ICancellationToken?)
            : IReferenceResolutionInfo {

        val content = this.contentManager.getLatestContent(document)

        val punctuation = charArrayOf(' ', '\t', '\r', '\n',
                '.', ',', ':', ';', '<', '>', '(', ')', '[', ']', '{', '}',
                '=', '+', '-', '*', '/')
        val referenceStart = content.text.lastIndexOfAny(punctuation, caretOffset.value - 1) + 1
        val referenceEnd = content.text.indexOfAny(punctuation, caretOffset.value)

        if (referenceStart == 0 || referenceEnd == -1 || referenceStart == referenceEnd) {
            // no reference
            return ReferenceResolutionInfo(null, emptyList())
        }

        val referenceSpan = Span(Offset(referenceStart), Offset(referenceEnd))
        val referenceText = content.text.substring(referenceStart, referenceEnd)

        val startOffset = content.text.indexOf(referenceText)
        if (startOffset == -1) {
            // no references
            return ReferenceResolutionInfo(referenceSpan, emptyList())
        }
        val endOffset = startOffset + referenceText.length

        if (referenceStart <= startOffset && endOffset <= referenceEnd) {
            // reference points to itself
            return ReferenceResolutionInfo(referenceSpan, emptyList())
        }

        return ReferenceResolutionInfo(referenceSpan, listOf(
                Definition(Symbol(referenceText, project, document, Span(Offset(startOffset), Offset(endOffset)), null, emptyList()))
        ))
    }
}