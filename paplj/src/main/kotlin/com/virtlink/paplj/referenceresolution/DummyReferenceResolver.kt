package com.virtlink.paplj.referenceresolution

import com.virtlink.editorservices.ICancellationToken
import com.virtlink.editorservices.IDocument
import com.virtlink.editorservices.IProject
import com.virtlink.editorservices.Span
import com.virtlink.editorservices.referenceresolution.Definition
import com.virtlink.editorservices.referenceresolution.IReferenceResolutionInfo
import com.virtlink.editorservices.referenceresolution.IReferenceResolver
import com.virtlink.editorservices.referenceresolution.ReferenceResolutionInfo

class DummyReferenceResolver : IReferenceResolver {
    override fun resolve(project: IProject, document: IDocument, caretOffset: Int, cancellationToken: ICancellationToken?)
            : IReferenceResolutionInfo {
        val punctuation = charArrayOf(' ', '\t', '\r', '\n',
                '.', ',', ':', ';', '<', '>', '(', ')', '[', ']', '{', '}',
                '=', '+', '-', '*', '/')
        val referenceStart = document.text.lastIndexOfAny(punctuation, caretOffset - 1) + 1
        val referenceEnd = document.text.indexOfAny(punctuation, caretOffset)

        if (referenceStart == 0 || referenceEnd == -1 || referenceStart == referenceEnd) {
            // no reference
            return ReferenceResolutionInfo(null, emptyList())
        }

        val referenceSpan = Span(referenceStart, referenceEnd)
        val referenceText = document.text.substring(referenceStart, referenceEnd)

        val startOffset = document.text.indexOf(referenceText)
        if (startOffset == -1) {
            // no references
            return ReferenceResolutionInfo(referenceSpan, emptyList())
        }
        val endOffset = startOffset + referenceText.length

        if (referenceStart <= startOffset && endOffset <= referenceEnd) {
            // reference points to itself
            return ReferenceResolutionInfo(referenceSpan, emptyList())
        }

        return ReferenceResolutionInfo(Span(referenceStart, referenceEnd), listOf(
                Definition(project, document, Span(startOffset, endOffset), referenceText)
        ))
    }
}