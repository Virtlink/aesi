package com.virtlink.editorservices.debugger

import com.virtlink.editorservices.IDocument
import com.virtlink.editorservices.IProject

interface IDebugger {
    fun canPutBreakpointAt(project: IProject, document: IDocument, line: Int): Boolean
}