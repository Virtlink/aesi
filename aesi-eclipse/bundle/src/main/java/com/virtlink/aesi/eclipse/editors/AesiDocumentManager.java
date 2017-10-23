package com.virtlink.aesi.eclipse.editors;

import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IFileEditorInput;

import java.net.URI;

//public class AesiDocumentManager {
//
//    private static final AesiDocumentManager instance = new AesiDocumentManager();
//    public static AesiDocumentManager getInstance() { return instance; }
//
//    public AesiEclipseDocument fromEditorInput(IEditorInput editorInput) {
//        if (editorInput instanceof IFileEditorInput) {
//            return fromEditorInput((IFileEditorInput)editorInput);
//        } else {
//            throw new IllegalStateException("Unsupported editor input.");
//        }
//    }
//
//    private AesiEclipseDocument fromEditorInput(IFileEditorInput fileEditorInput) {
//        IAesiProject project = null;
////        fileEditorInput.getFile().getProject()
//        URI uri = fileEditorInput.getFile().getLocationURI();
//        return new AesiFileDocument(project, uri);
//    }
//
//}
