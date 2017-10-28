package com.virtlink.editorservices.eclipse.content;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import org.eclipse.core.filebuffers.FileBuffers;
import org.eclipse.core.filebuffers.ITextFileBuffer;
import org.eclipse.core.filebuffers.ITextFileBufferManager;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IStorage;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.text.IDocument;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IPathEditorInput;
import org.eclipse.ui.IStorageEditorInput;
import org.eclipse.ui.IURIEditorInput;

import com.virtlink.editorservices.content.IContent;
import com.virtlink.editorservices.content.StringContent;
import com.virtlink.editorservices.documents.IResourceManager;
import com.virtlink.editorservices.eclipse.Contract;
import com.virtlink.editorservices.eclipse.editor.AesiDocumentProvider;

/**
 * Manages the documents in an Eclipse project.
 */
public class EclipseResourceManager implements IResourceManager {
	
	private final static String ECLIPSE_SCHEMA = "eclipse";
	
	// See also:
	// https://help.eclipse.org/mars/index.jsp?topic=%2Forg.eclipse.platform.doc.isv%2Fguide%2FresInt_filesystem.htm
	
	/**
	 * Maps the document to its corresponding resource.
	 * 
	 * This will check every resource to see whether it corresponds
	 * to the given document. Do not use this method in performance-critical
	 * applications.
	 * 
	 * @param document The document.
	 * @return The resource, or null when not found.
	 */
	@Nullable
	public IResource getResource(IDocument document) {
		Contract.requireNotNull("document", document);
		
		IResource resource = AesiDocumentProvider.getInstance().getFile(document);
		if (resource != null) { return resource; }
		
		final ITextFileBufferManager bufferManager = FileBuffers.getTextFileBufferManager();
		if (bufferManager == null) { return null; }
		final ITextFileBuffer buffer = bufferManager.getTextFileBuffer(document);
		if (buffer == null) { return null; }
		final IPath location = buffer.getLocation();
		return ResourcesPlugin.getWorkspace().getRoot().findMember(location);
	}

	/**
	 * Maps the editor to its corresponding resource.
	 * 
	 * @param editor The editor.
	 * @return The resource.
	 */
	public IResource getResource(IEditorPart editor) {
		Contract.requireNotNull("editor", editor);
		
		return getResource(editor.getEditorInput());
	}

	/**
	 * Maps editor input to its corresponding resource.
	 * 
	 * @param input The input.
	 * @return The resource.
	 */
	public IResource getResource(IEditorInput input) {
		Contract.requireNotNull("input", input);
		
		if(input instanceof IFileEditorInput) {
            final IFileEditorInput fileInput = (IFileEditorInput)input;
            return fileInput.getFile();
        } else if(input instanceof IPathEditorInput) {
            final IPathEditorInput pathInput = (IPathEditorInput)input;
            IPath path = pathInput.getPath();
//            return resolve(pathInput.getPath());
    		throw new RuntimeException("Not implemented.");
        } else if(input instanceof IURIEditorInput) {
            final IURIEditorInput uriInput = (IURIEditorInput) input;
            URI uri = uriInput.getURI();

    		throw new RuntimeException("Not implemented.");
        } else if(input instanceof IStorageEditorInput) {
            final IStorageEditorInput storageInput = (IStorageEditorInput) input;
            final IStorage storage;
            try {
                storage = storageInput.getStorage();
            } catch(CoreException e) {
                return null;
            }

            final IPath path = storage.getFullPath();
            if(path != null) {
                //return path;
        		throw new RuntimeException("Not implemented.");
            } else {
                //return "mem:///" + input.getName();
        		throw new RuntimeException("Not implemented.");
            }
        } else {
    		throw new RuntimeException("Not implemented.");
        }
	}
	
	/**
	 * Maps a URI to its corresponding resource.
	 * 
	 * @param uri The URI.
	 * @return The resource; or null when not found.
	 */
	@Nullable public IResource getResource(URI uri) {
		Contract.requireNotNull("uri", uri);
		
		if (uri.getScheme().equals(ECLIPSE_SCHEMA)) {
			final String path = uri.getPath();
			int split = path.indexOf('!');
			if (split == -1) {
				split = path.length();
			}
			
			// The project path starts with a slash, since it's an absolute path.
			String projectName = path.substring(1, split);
			if (projectName.endsWith("/"))
				projectName = projectName.substring(0, projectName.length() - 1);
			
			@Nullable String resourcePath;
			if (split < path.length()) {
				resourcePath = path.substring(split + 1);
				if (resourcePath.startsWith("/")) {
					resourcePath = resourcePath.substring(1);
				}
			} else {
				resourcePath = null;
			}

			final IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
			final IProject project = workspaceRoot.getProject(projectName);
			if (project == null) {
				return null;
			}
			final IResource resource;
			if (resourcePath != null) {
				resource = project.findMember(resourcePath);
			} else {
				resource = project;
			}
			return resource;
		} else {
			throw new RuntimeException("Not implemented");
		}
		
//				
//		IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
//		final String path = null;
//        IResource resource = workspaceRoot.findMember(path);
//        if(resource != null) {
//            // Path might be absolute, try to get absolute file.
//            final IPath location = Path.fromOSString(uri.getPath());
//            resource = workspaceRoot.getFileForLocation(location);
//            if(resource == null) {
//                // If resource is a direct path to a project, getContainerForLocation needs to be used.
//            	resource = workspaceRoot.getContainerForLocation(location);
//            }
//        }
//        return resource;
	}
	
	/**
	 * Maps the document to its corresponding URI.
	 * 
	 * Do not use in performance-critical code.
	 * 
	 * @param document The document.
	 * @return The URI.
	 */
	public URI getUri(IDocument document) {
		Contract.requireNotNull("document", document);
		
		return getUri(getResource(document));
	}

	/**
	 * Maps the editor to its corresponding URI.
	 * 
	 * @param editor The editor.
	 * @return The URI.
	 */
	public URI getUri(IEditorPart editor) {
		Contract.requireNotNull("editor", editor);
		
		return getUri(editor.getEditorInput());
	}
	
	/**
	 * Maps editor input to its corresponding URI.
	 * 
	 * @param input The input.
	 * @return The URI.
	 */
	public URI getUri(IEditorInput input) {
		Contract.requireNotNull("input", input);
		
		return getUri(getResource(input));		
	}
	
	/**
	 * Maps a resource to its corresponding URI.
	 * 
	 * @param resource The resource.
	 * @return The URI.
	 */
	public URI getUri(IResource resource) {
		Contract.requireNotNull("resource", resource);

		String projectUri = String.format("%s://%s/", ECLIPSE_SCHEMA, resource.getProject().getFullPath());
		if (resource instanceof IProject) {
			// EXAMPLE eclipse:///myproject/
			return toURI(projectUri);
		} else if (resource instanceof IFile) {
			// EXAMPLE eclipse:///myproject/!/subfolder/myfile
			// File URIs must not end with a slash.
			return toURI(String.format("%s!/%s", projectUri, resource.getProjectRelativePath()));
		} else if (resource instanceof IFolder) {
			// EXAMPLE eclipse:///myproject/!/subfolder/myfolder/
			// Folder URIs have to end with a slash.
			return toURI(String.format("%s!/%s/", projectUri, resource.getProjectRelativePath()));
		} else {
			throw new RuntimeException("Not implemented.");
		}
	}

	@Override
	public URI getProject(URI uri) {
		Contract.requireNotNull("uri", uri);
		
		IResource resource = getResource(uri);
		if (resource == null)
			return null;
		IProject project = resource.getProject();
		if (project == null)
			return null;
		return getUri(project);
	}

	@Override
	public boolean isFile(URI uri) {
		Contract.requireNotNull("uri", uri);
		
		IResource resource = getResource(uri);
		return resource instanceof IFile;
	}

	@Override
	public boolean isFolder(URI uri) {
		Contract.requireNotNull("uri", uri);
		
		IResource resource = getResource(uri);
		return resource instanceof IFolder;
	}

	@Override
	public boolean isProject(URI uri) {
		Contract.requireNotNull("uri", uri);
		
		IResource resource = getResource(uri);
		return resource instanceof IProject;
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
			IFile file = (IFile)resource;
			AesiDocumentProvider documentProvider = AesiDocumentProvider.getInstance();
			try {
				documentProvider.connect(file);
			} catch (CoreException e) {
				// File does not exist or cannot be turned into a document.
				return null;
			}
			long stamp = documentProvider.getModificationStamp(file);
			IDocument document = documentProvider.getDocument(file);
			return getContent(document, stamp);
		} else {
			// Resource not found or not a file.
			return null;
		}
	}
	
	/**
	 * Turns an Eclipse document into AESI content.
	 * 
	 * @param document The Eclipse document.
	 * @param stamp The modification stamp.
	 * @return The AESI content.
	 */
	private IContent getContent(IDocument document, long stamp) {
		// TODO: Use stamp.
		return new StringContent(document.get());
	}

	/**
	 * Converts the URI string to an actual URI.
	 * 
	 * @param uri The URI string to convert.
	 * @return The resulting URI.
	 */
	private static URI toURI(String uri) {
		try {
			return new URI(uri);
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}

}
