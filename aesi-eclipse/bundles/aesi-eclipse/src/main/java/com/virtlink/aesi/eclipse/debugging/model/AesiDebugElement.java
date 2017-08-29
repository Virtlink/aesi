package com.virtlink.aesi.eclipse.debugging.model;

import com.virtlink.aesi.eclipse.AesiPlugin;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.IBreakpointManager;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.model.DebugElement;
import org.eclipse.debug.core.model.IDebugTarget;

/**
 * A debug element.
 */
public class AesiDebugElement extends DebugElement {

    /**
     * Gets the debug target.
     *
     * @return The debug target.
     */
    public AesiDebugTarget getAesiDebugTarget() { return (AesiDebugTarget)super.getDebugTarget(); }

    /**
     * Initializes a new instance of the {@link AesiDebugElement} class.
     *
     * @param target The debug target.
     */
    public AesiDebugElement(AesiDebugTarget target) {
        super(target);
    }

    /** {@inheritDoc} */
    @Override
    public String getModelIdentifier() { return AesiPlugin.getDebugModelIdentifier(); }

    /**
     * Gets the breakpoint manager.
     *
     * @return The breakpoint manager.
     */
    protected IBreakpointManager getBreakpointManager() {
        return DebugPlugin.getDefault().getBreakpointManager();
    }
}
