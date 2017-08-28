package org.metaborg.paplj.debugger

import com.google.common.collect.Maps
import com.intellij.xdebugger.XSourcePosition
import com.intellij.xdebugger.breakpoints.XBreakpointHandler
import com.intellij.xdebugger.breakpoints.XBreakpointProperties
import com.intellij.xdebugger.breakpoints.XBreakpointType
import com.intellij.xdebugger.breakpoints.XLineBreakpoint

class AesiLineBreakpointHandler(breakpointTypeClass: Class<out XBreakpointType<XLineBreakpoint<XBreakpointProperties<*>>, *>>)
    : XBreakpointHandler<XLineBreakpoint<XBreakpointProperties<*>>>(breakpointTypeClass) {

    private val breakPointPositions = Maps.newHashMap<XLineBreakpoint<XBreakpointProperties<*>>, XSourcePosition>()

    override fun registerBreakpoint(breakpoint: XLineBreakpoint<XBreakpointProperties<*>>) {
        val position = breakpoint.sourcePosition
        if (position != null) {
            // TODO: IDebugger.setBreakpoint
//            myDebugProcess.addBreakpoint(myDebugProcess.getPositionConverter().convertToPython(position), breakpoint)
            this.breakPointPositions.put(breakpoint, position)
        }
    }

    override fun unregisterBreakpoint(breakpoint: XLineBreakpoint<XBreakpointProperties<*>>, temporary: Boolean) {
        val position = this.breakPointPositions[breakpoint]
        if (position != null) {
            // TODO: IDebugger.unsetBreakpoint
//            myDebugProcess.removeBreakpoint(myDebugProcess.getPositionConverter().convertToPython(position))
            this.breakPointPositions.remove(breakpoint)
        }
    }

}

