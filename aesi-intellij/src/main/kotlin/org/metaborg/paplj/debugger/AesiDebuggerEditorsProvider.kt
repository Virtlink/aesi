package org.metaborg.paplj.debugger

import com.intellij.openapi.editor.Document
import com.intellij.openapi.fileTypes.FileType
import com.intellij.openapi.project.Project
import com.intellij.xdebugger.XSourcePosition
import com.intellij.xdebugger.evaluation.EvaluationMode
import com.intellij.xdebugger.evaluation.XDebuggerEditorsProvider
import org.metaborg.paplj.AesiFileType

class AesiDebuggerEditorsProvider : XDebuggerEditorsProvider() {
    override fun createDocument(project: Project, text: String, sourcePosition: XSourcePosition?, mode: EvaluationMode): Document {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getFileType(): FileType {
        return AesiFileType
    }

}