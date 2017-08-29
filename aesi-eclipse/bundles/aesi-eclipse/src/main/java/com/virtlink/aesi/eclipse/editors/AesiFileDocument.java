package com.virtlink.aesi.eclipse.editors;

import com.virtlink.aesi.IAesiDocument;
import com.virtlink.aesi.IAesiProject;

import java.net.URI;

/**
 * A file-backed document.
 */
public class AesiFileDocument implements IAesiDocument {

    private final IAesiProject project;
    private final URI uri;

    @Override
    public IAesiProject getProject() { return this.project; }

    @Override
    public URI getUri() { return this.uri; }

    public AesiFileDocument(IAesiProject project, URI uri) {
        this.project = project;
        this.uri = uri;
    }

    @Override
    public String toString() {
        return this.uri.toString();
    }
}
