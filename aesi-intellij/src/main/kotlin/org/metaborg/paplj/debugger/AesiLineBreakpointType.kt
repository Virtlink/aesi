package org.metaborg.paplj.debugger

import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.xdebugger.breakpoints.XLineBreakpointTypeBase

class AesiLineBreakpointType: XLineBreakpointTypeBase(ID, NAME, AesiDebuggerEditorsProvider()) {
    companion object {
        val ID = "aesi-line"
        private val NAME = "Line Breakpoint"
    }

    override fun canPutAt(file: VirtualFile, line: Int, project: Project): Boolean {
        // TODO IDebugger.canPutBreakpointAt
        return super.canPutAt(file, line, project)
    }

}

