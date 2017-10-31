package com.virtlink.paplj.referenceresolution

import com.google.inject.Inject
import com.virtlink.editorservices.*
import com.virtlink.editorservices.resources.IResourceManager
import com.virtlink.editorservices.referenceresolution.*
import com.virtlink.editorservices.symbols.Symbol
import com.virtlink.logging.logger
import java.net.URI

class DummyReferenceResolver @Inject constructor(private val resourceManager: IResourceManager) : IReferenceResolverService {

    @Suppress("PrivatePropertyName")
    private val LOG by logger()

    override fun resolve(document: URI, caretOffset: Offset, cancellationToken: ICancellationToken)
            : IReferenceResolutionInfo? {

        val content = this.resourceManager.getContent(document) ?: return null

        val punctuation = charArrayOf(' ', '\t', '\r', '\n',
                '.', ',', ':', ';', '<', '>', '(', ')', '[', ']', '{', '}',
                '=', '+', '-', '*', '/')
        val referenceStart = content.text.lastIndexOfAny(punctuation, (caretOffset - 1).toInt()) + 1
        val referenceEnd = content.text.indexOfAny(punctuation, caretOffset.toInt())

        if (referenceStart == 0 || referenceEnd == -1 || referenceStart == referenceEnd) {
            // no reference
            return ReferenceResolutionInfo(null, emptyList())
        }

        val referenceSpan = Span(referenceStart.toLong(), referenceEnd.toLong())
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
                Definition(Symbol(referenceText, document, Span(startOffset.toLong(), endOffset.toLong()), null, emptyList()))
        ))
    }
}