package com.virtlink.aesi.eclipse.debugging.model;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.debug.ui.actions.IToggleBreakpointsTarget;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.texteditor.ITextEditor;

import com.virtlink.aesi.eclipse.editors.AesiEditor;

import javax.annotation.Nullable;

public class AesiBreakpointAdapterFactory implements IAdapterFactory {
    public Object getAdapter(Object adaptableObject, Class adapterType) {
        if (adaptableObject instanceof AesiEditor) {
        	AesiEditor editorPart = (AesiEditor) adaptableObject;
//            IResource resource = (IResource) editorPart.getEditorInput().getAdapter(IResource.class);
//            if (resource != null) {
//                @Nullable String extension = resource.getFileExtension();
//                if (extension != null && extension.equals("pda")) {
                    return new AesiLineBreakpointAdapter();
//                }
//            }
        }
        return null;
    }

    @Override
    public Class[] getAdapterList() {
        return new Class[] { IToggleBreakpointsTarget.class };
    }
}
