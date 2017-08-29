package com.virtlink.aesi.eclipse.debugging.model;

import com.virtlink.aesi.eclipse.AesiPlugin;
import org.eclipse.debug.core.model.Breakpoint;

public class AesiBreakpoint extends Breakpoint {

    private int hitCount;

    /**
     * Gets the number of times this breakpoint has been hit.
     *
     * @return The hit count.
     */
    public int getHitCount() { return this.hitCount; }

    /**
     * Sets the number of times this breakpoint has been hit.
     *
     * @param value The hit count.
     */
    public void setHitCount(int value) { this.hitCount = value; }

    /** {@inheritDoc} */
    @Override
    public String getModelIdentifier() {
        return AesiPlugin.getDebugModelIdentifier();
    }
}
