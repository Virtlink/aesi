package com.virtlink.aesi.eclipse.debugging.model;

import com.virtlink.aesi.eclipse.AesiPlugin;
import com.virtlink.aesi.eclipse.editors.AesiSourceScanner;
import com.virtlink.aesi.eclipse.logging.LoggerFactory;
import org.eclipse.core.resources.IMarkerDelta;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.IBreakpointListener;
import org.eclipse.debug.core.model.IBreakpoint;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * Manages breakpoints.
 */
public final class AesiBreakpointManager {

    private static final Logger log = LoggerFactory.getLogger(AesiBreakpointManager.class);

    private final AesiDebugThread thread;
    @Nullable
    private BreakpointListener listener = null;

    /**
     * Initializes a new instance of the {@link AesiBreakpointManager} class.
     *
     * @param thread The debug thread.
     */
    public AesiBreakpointManager(AesiDebugThread thread) {
        this.thread = thread;

        addPresetBreakpoints();
        registerBreakpointListener();
    }

    /**
     * Disposes this object.
     */
    public void dispose() {
        unregisterBreakpointListener();
    }

    /**
     * Returns all set Eclipse breakpoints.
     *
     * @return A list of breakpoints.
     */
    private List<AesiBreakpoint> getEclipseBreakpoints() {
        return Stream.of(DebugPlugin.getDefault().getBreakpointManager()
                .getBreakpoints(AesiPlugin.getDebugModelIdentifier()))
                .filter(AesiBreakpoint.class::isInstance)
                .map(AesiBreakpoint.class::cast)
                .collect(toList());
    }

    /**
     * Adds all breakpoints that were set before the debugger was running.
     */
    public void addPresetBreakpoints() {
        List<AesiBreakpoint> breakpoints = getEclipseBreakpoints();

        for (AesiBreakpoint breakpoint : breakpoints)
        {
            try {
                if (!breakpoint.isEnabled())
                    continue;
            } catch (CoreException e) {
                log.error("Unexpected exception.", e);
            }

            addBreakpoint(breakpoint);
        }
    }

    private void addBreakpoint(AesiBreakpoint breakpoint) {
        // TODO
    }

    /**
     * Registers the breakpoint listener.
     */
    private void registerBreakpointListener() {
        if (this.listener == null)
            this.listener = new BreakpointListener();
        DebugPlugin.getDefault().getBreakpointManager().addBreakpointListener(this.listener);
    }

    /**
     * Unregisters the breakpoint listener.
     */
    private void unregisterBreakpointListener() {
        if (this.listener == null)
            return;
        DebugPlugin.getDefault().getBreakpointManager().removeBreakpointListener(this.listener);
        this.listener = null;
    }

    /**
     * Handles when breakpoints are added, changed, or removed
     * while the debugger is running.
     */
    private class BreakpointListener implements IBreakpointListener {

        /** {@inheritDoc} */
        @Override
        public void breakpointAdded(IBreakpoint breakpoint) {

        }

        /** {@inheritDoc} */
        @Override
        public void breakpointChanged(IBreakpoint breakpoint, IMarkerDelta delta) {

        }

        /** {@inheritDoc} */
        @Override
        public void breakpointRemoved(IBreakpoint breakpoint, IMarkerDelta delta) {

        }
    }

}
