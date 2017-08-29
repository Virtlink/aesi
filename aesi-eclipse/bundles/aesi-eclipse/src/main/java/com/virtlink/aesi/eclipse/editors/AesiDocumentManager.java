package com.virtlink.aesi.eclipse.editors;

import com.virtlink.aesi.IAesiDocument;
import com.virtlink.aesi.IAesiProject;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IFileEditorInput;

import java.net.URI;

public class AesiDocumentManager {

    private static final AesiDocumentManager instance = new AesiDocumentManager();
    public static AesiDocumentManager getInstance() { return instance; }

    public IAesiDocument fromEditorInput(IEditorInput editorInput) {
        if (editorInput instanceof IFileEditorInput) {
            return fromEditorInput((IFileEditorInput)editorInput);
        } else {
            throw new IllegalStateException("Unsupported editor input.");
        }
    }

    private IAesiDocument fromEditorInput(IFileEditorInput fileEditorInput) {
        IAesiProject project = null;
//        fileEditorInput.getFile().getProject()
        URI uri = fileEditorInput.getFile().getLocationURI();
        return new AesiFileDocument(project, uri);
    }

}
