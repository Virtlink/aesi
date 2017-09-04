package com.virtlink.aesi.eclipse.debugging.model;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.debug.core.model.ILineBreakpoint;
import org.eclipse.debug.core.model.LineBreakpoint;

import javax.annotation.Nullable;

/**
 * A breakpoint that is tied to a specific line in the source code.
 *
 * This class maintains a breakpoint marker that is displayed to the user.
 */
public class AesiLineBreakpoint extends AesiBreakpoint implements ILineBreakpoint {

    private final static String MARKER_ID = "com.virtlink.aesi.eclipse.debugging.breakpointMarker";

    private boolean isConditionEnabled;
    @Nullable private String condition = null;

    public AesiLineBreakpoint() {
        // Nothing to do.
    }

    public AesiLineBreakpoint(IResource resource, int lineNumber) throws CoreException {
        IMarker marker = resource.createMarker(MARKER_ID);

        marker.setAttribute(IBreakpoint.ID, getModelIdentifier());
        marker.setAttribute(IMarker.SOURCE_ID, resource.getName());
        marker.setAttribute(IMarker.LINE_NUMBER, lineNumber);
        marker.setAttribute(IMarker.MESSAGE, "Line Breakpoint: " + resource.getName()
                + " [line: " + lineNumber + "]");

        setMarker(marker);
        setEnabled(true);
    }

    /**
     * Gets the file that contains the breakpoint.
     *
     * @return The file.
     */
    public IFile getFile() {
        return (IFile)this.getMarker().getResource();
    }

    /**
     * Gets the line number from the breakpoint marker.
     *
     * @return The line number.
     */
    public int getLine() {
        try {
            return getLineNumber();
        } catch (CoreException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Returns whether the breakpoint is enabled.
     *
     * @return {@code true} when the breakpoint is enabled;
     * otherwise, {@code false}.
     */
    public boolean isEnabled() {
        try {
            return super.isEnabled();
        } catch (CoreException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Gets whether the condition is enabled on this breakpoint.
     *
     * @return {@code true} when the condition is enabled;
     * otherwise, {@code false}.
     */
    public boolean isConditionEnabled() { return this.isConditionEnabled; }

    /**
     * Sets whether the condition is enabled on this breakpoint.
     *
     * @param value {@code true} to enable the condition;
     * otherwise, {@code false}.
     */
    public void setIsConditionEnabled(boolean value) { this.isConditionEnabled = value; }

    /**
     * Gets the condition on which this breakpoint triggers.
     * @return The condition expression; or {@code null} when none is set.
     */
    @Nullable
    public String getCondition() { return this.condition; }

    /**
     * Sets the condition on which this breakpoint triggers.
     * @param value The condition expression; or {@code null} to set none.
     */
    public void setCondition(@Nullable String value) { this.condition = value; refresh(); }

    @Override
    public int getLineNumber() throws CoreException {
        @Nullable IMarker marker = getMarker();
        return marker != null ? marker.getAttribute(IMarker.LINE_NUMBER, -1) : -1;
    }

    @Override
    public int getCharStart() throws CoreException {
        @Nullable IMarker marker = getMarker();
        return marker != null ? marker.getAttribute(IMarker.CHAR_START, -1) : -1;
    }

    @Override
    public int getCharEnd() throws CoreException {
        @Nullable IMarker marker = getMarker();
        return marker != null ? marker.getAttribute(IMarker.CHAR_END, -1) : -1;
    }

    public void refresh() {
        // TODO: Refresh the breakpoint; it's attributes have changed.
    }
}
