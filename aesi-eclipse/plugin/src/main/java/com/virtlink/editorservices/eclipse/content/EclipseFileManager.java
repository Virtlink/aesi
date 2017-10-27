package com.virtlink.editorservices.eclipse.content;

import java.net.URI;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;

import com.virtlink.editorservices.content.IContent;
import com.virtlink.editorservices.documents.IFileManager;
import com.virtlink.editorservices.eclipse.Contract;

/**
 * Manages the documents in an Eclipse project.
 */
public class EclipseFileManager implements IFileManager {
	
	// See also:
	// https://help.eclipse.org/mars/index.jsp?topic=%2Forg.eclipse.platform.doc.isv%2Fguide%2FresInt_filesystem.htm
	
	/**
	 * Maps a resource to its corresponding URI.
	 * 
	 * @param resource The resource.
	 * @return The URI.
	 */
	public URI getUri(IResource resource) {
		Contract.requireNotNull("resource", resource);
		
		return resource.getLocationURI();
	}
	
	/**
	 * Maps a URI to its corresponding resource.
	 * 
	 * @param uri The URI.
	 * @return The resource.
	 */
	public IResource getResource(URI uri) {
		Contract.requireNotNull("uri", uri);
		
		IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
//		workspaceRoot.fin
//		IProject myWebProject = myWorkspaceRoot.getProject("MyWeb");
//		   // open if necessary
//		   if (myWebProject.exists() && !myWebProject.isOpen())
//		      myWebProject.open(null);
		throw new RuntimeException("Not implemented.");
	}

	@Override
	public boolean exists(URI uri) {
		Contract.requireNotNull("uri", uri);
		
		return getResource(uri).exists();
	}

	@Override
	public Iterable<URI> getChildren(URI uri) {
		Contract.requireNotNull("uri", uri);
		
		IResource resource = getResource(uri);
		if (resource instanceof IFolder) {
			IFolder folder = (IFolder)resource;
			try {
				return Stream.of(folder.members())
					.map(r -> getUri(r))
					.collect(Collectors.toList());
			} catch (CoreException e) {
				// Folder does not exist or is not accessible. 
				return null;
			}
		} else {
			// Resource not found or not a folder.
			return null;
		}
	}

	@Override
	public IContent getContent(URI uri) {
		Contract.requireNotNull("uri", uri);

		IResource resource = getResource(uri);
		if (resource instanceof IFile) {
			
		} else {
			// Resource not found or not a file.
			return null;
		}
	}

}
