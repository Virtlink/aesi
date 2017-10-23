package com.virtlink.aesi.eclipse;

import com.virtlink.editorservices.IProject;
import org.jetbrains.annotations.NotNull;

import java.net.URI;
import java.net.URISyntaxException;

public class EclipseProject implements IProject {
    @NotNull
    @Override
    public URI getUri() {
        try {
            return new URI("project");
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
    // TODO: Store IProject
//	private final org.eclipse.core.resources.IProject project;
//	
//	public EclipseProject(final org.eclipse.core.resources.IProject project) {
//		if (project == null)
//			throw new IllegalArgumentException("project must not be null.");
//		
//		this.project = project;
//	}
	
	
}
