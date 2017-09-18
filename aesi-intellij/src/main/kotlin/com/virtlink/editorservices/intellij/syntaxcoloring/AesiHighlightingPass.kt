package com.virtlink.editorservices.intellij.syntaxcoloring

import com.google.inject.Inject
import com.google.inject.assistedinject.Assisted
import com.intellij.codeHighlighting.TextEditorHighlightingPass
import com.intellij.codeInsight.daemon.impl.UpdateHighlightersUtil
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.progress.ProgressIndicator
import com.intellij.openapi.project.Project
import com.virtlink.editorservices.syntaxcoloring.ISyntaxColorer
import com.intellij.codeInsight.daemon.impl.HighlightInfo
import java.util.*
import com.intellij.codeInsight.daemon.impl.DaemonCodeAnalyzerImpl
import com.intellij.codeInsight.daemon.DaemonCodeAnalyzer
import com.intellij.psi.PsiFile
import com.intellij.compiler.progress.CompilerTask.getTextRange
import com.intellij.util.xml.DomUtil.getValueElement
import com.intellij.psi.xml.XmlAttributeValue
import com.intellij.psi.XmlRecursiveElementVisitor
import com.intellij.util.SmartList
import com.intellij.usages.ChunkExtractor.getStartOffset
import com.intellij.openapi.util.TextRange
import com.intellij.codeInsight.daemon.impl.HighlightInfoType
import com.virtlink.editorservices.Span
import com.virtlink.editorservices.intellij.toCancellationToken
import com.virtlink.editorservices.syntaxcoloring.IToken

class AesiHighlightingPass @Inject constructor(
        private val colorizer: ISyntaxColorer,
        @Assisted private val file: PsiFile,
        @Assisted private val editor: Editor,
        @Assisted private val startOffset: Int,
        @Assisted private val endOffset: Int)
    : TextEditorHighlightingPass(file.project, editor.document) {

//    @Volatile private var highlights = Collections.emptyList<HighlightInfo>()
    @Volatile private var tokens = Collections.emptyList<IToken>()
    private val colorizerAdapter = AesiSyntaxColorerAdapter(editor, startOffset, endOffset)

    override fun doCollectInformation(progress: ProgressIndicator) {
        // TODO: Project, document
        val project = com.virtlink.editorservices.Project("NAME")
        val document = com.virtlink.editorservices.Document("NAME")
        this.tokens = colorizer.highlight(project, document, Span(this.startOffset, this.endOffset), progress.toCancellationToken())
//        this.highlights = tokens.map { t -> tokenToHighlightInfo(t) }.toList()

    }

//    private fun tokenToHighlightInfo(token: IToken): HighlightInfo {
//        val style = getStyle(token)
//        return HighlightInfo.newHighlightInfo(style)
//                .range(token.location.startOffset, token.location.endOffset)
//                .createUnconditionally()
//    }
//
//    private fun getStyle(token: IToken): HighlightInfoType {
//        return HighlightInfoType.DEPRECATED
//    }

    override fun doApplyInformationToEditor() {
        colorizerAdapter.colorize(this.tokens)
//        UpdateHighlightersUtil.setHighlightersToEditor(myProject, myDocument!!, startOffset, endOffset, highlights, colorsScheme, id)
//        val daemonCodeAnalyzer = DaemonCodeAnalyzer.getInstance(myProject)
//        (daemonCodeAnalyzer as DaemonCodeAnalyzerImpl).fileStatusMap.markFileUpToDate(myDocument, id)
    }
}