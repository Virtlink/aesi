package com.virtlink.aesi.eclipse.editors;

import com.virtlink.editorservices.IDocument;
import com.virtlink.editorservices.IProject;

import java.net.URI;

/**
 * A file-backed document.
 */
public class AesiFileDocument implements IDocument {

    private final IProject project;
    private final URI uri;

    public IProject getProject() { return this.project; }

    @Override
    public URI getUri() { return this.uri; }

    public AesiFileDocument(IProject project, URI uri) {
        this.project = project;
        this.uri = uri;
    }

    @Override
    public String toString() {
        return this.uri.toString();
    }
}
