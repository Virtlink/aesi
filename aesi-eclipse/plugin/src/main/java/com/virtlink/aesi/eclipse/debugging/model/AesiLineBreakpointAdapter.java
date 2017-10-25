package com.virtlink.aesi.eclipse.debugging.model;

import com.virtlink.aesi.eclipse.AesiPlugin;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.IBreakpointManager;
import org.eclipse.debug.ui.actions.IToggleBreakpointsTarget;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.texteditor.ITextEditor;

import javax.annotation.Nullable;
import java.util.stream.Stream;

public class AesiLineBreakpointAdapter implements IToggleBreakpointsTarget {
    @Override
    public void toggleLineBreakpoints(IWorkbenchPart part, ISelection selection) throws CoreException {
        @Nullable ITextEditor editor = getEditor(part);
        if (editor == null)
            return;

        IResource resource = (IResource)editor.getEditorInput().getAdapter(IResource.class);
        ITextSelection textSelection = (ITextSelection) selection;
        int lineNumber = textSelection.getStartLine() + 1;
        @Nullable AesiLineBreakpoint breakpoint = getFirstBreakpoint(resource, lineNumber);

        // Toggle
        if (breakpoint != null) {
            // Remove the existing breakpoint.
            breakpoint.delete();
        } else {
            // Add a new breakpoint.
            breakpoint = new AesiLineBreakpoint(resource, lineNumber);
            DebugPlugin.getDefault().getBreakpointManager().addBreakpoint(breakpoint);
        }
    }

    @Nullable
    private static ITextEditor getEditor(@Nullable IWorkbenchPart part) {
        if (part == null || !(part instanceof ITextEditor))
            return null;
        return (ITextEditor)part;
    }

    @Nullable
    private AesiLineBreakpoint getFirstBreakpoint(IResource resource, int lineNumber) {
        IBreakpointManager breakpointManager = DebugPlugin.getDefault().getBreakpointManager();
        return Stream.of(breakpointManager.getBreakpoints(AesiPlugin.getDebugModelIdentifier()))
                .filter(bp -> bp instanceof AesiLineBreakpoint)
                .map(bp -> (AesiLineBreakpoint)bp)
                .filter(bp -> resource.equals(bp.getMarker().getResource()) && bp.getLine() == lineNumber)
                .findFirst()
                .orElse(null);
    }

    @Override
    public boolean canToggleLineBreakpoints(IWorkbenchPart part, ISelection selection) {
        // Supported.
        return true;
    }

    @Override
    public void toggleMethodBreakpoints(IWorkbenchPart part, ISelection selection) throws CoreException {
        // Not supported.
    }

    @Override
    public boolean canToggleMethodBreakpoints(IWorkbenchPart part, ISelection selection) {
        // Not supported.
        return false;
    }

    @Override
    public void toggleWatchpoints(IWorkbenchPart part, ISelection selection) throws CoreException {
        // Not supported.
    }

    @Override
    public boolean canToggleWatchpoints(IWorkbenchPart part, ISelection selection) {
        // Not supported.
        return false;
    }
}
