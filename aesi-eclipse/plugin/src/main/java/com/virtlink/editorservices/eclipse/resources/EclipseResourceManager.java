package com.virtlink.editorservices.eclipse.resources;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import com.sun.scenario.effect.Offset;
import com.virtlink.editorservices.Position;
import com.virtlink.editorservices.resources.IAesiContent;
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
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextOperationTarget;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IPathEditorInput;
import org.eclipse.ui.IStorageEditorInput;
import org.eclipse.ui.IURIEditorInput;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.texteditor.ITextEditor;

import com.virtlink.editorservices.resources.IContent;
import com.virtlink.editorservices.content.StringContent;
import com.virtlink.editorservices.resources.IResourceManager;
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
	 * Maps the document to its corresponding file.
	 * 
	 * This will check every resource to see whether it corresponds
	 * to the given document. Do not use this method in performance-critical
	 * applications.
	 * 
	 * @param document The document.
	 * @return The file, or null when not found.
	 */
	@Nullable
	public IFile getFile(IDocument document) {
		Contract.requireNotNull("document", document);
		
		IFile file = AesiDocumentProvider.getInstance().getFile(document);
		if (file != null) { return file; }
		
		final ITextFileBufferManager bufferManager = FileBuffers.getTextFileBufferManager();
		if (bufferManager == null) { return null; }
		final ITextFileBuffer buffer = bufferManager.getTextFileBuffer(document);
		if (buffer == null) { return null; }
		final IPath location = buffer.getLocation();
		if (location == null) { return null; }
		return ResourcesPlugin.getWorkspace().getRoot().getFile(location);
//		return ResourcesPlugin.getWorkspace().getRoot().findMember(location);
	}

	/**
	 * Maps the editor to its corresponding file.
	 * 
	 * @param editor The editor.
	 * @return The file.
	 */
	public IFile getFile(IEditorPart editor) {
		Contract.requireNotNull("editor", editor);
		
		return getFile(editor.getEditorInput());
	}

	/**
	 * Maps editor input to its corresponding resource.
	 * 
	 * @param input The input.
	 * @return The resource.
	 */
	public IFile getFile(IEditorInput input) {
		Contract.requireNotNull("input", input);
		
		return asFile(input.getAdapter(IResource.class));
//		
//		if(input instanceof IFileEditorInput) {
//            final IFileEditorInput fileInput = (IFileEditorInput)input;
//            return fileInput.getFile();
//        } else if(input instanceof IPathEditorInput) {
//            final IPathEditorInput pathInput = (IPathEditorInput)input;
//            IPath path = pathInput.getPath();
////            return resolve(pathInput.getPath());
//    		throw new RuntimeException("Not implemented.");
//        } else if(input instanceof IURIEditorInput) {
//            final IURIEditorInput uriInput = (IURIEditorInput) input;
//            URI uri = uriInput.getURI();
//
//    		throw new RuntimeException("Not implemented.");
//        } else if(input instanceof IStorageEditorInput) {
//            final IStorageEditorInput storageInput = (IStorageEditorInput) input;
//            final IStorage storage;
//            try {
//                storage = storageInput.getStorage();
//            } catch(CoreException e) {
//                return null;
//            }
//
//            final IPath path = storage.getFullPath();
//            if(path != null) {
//                //return path;
//        		throw new RuntimeException("Not implemented.");
//            } else {
//                //return "mem:///" + input.getName();
//        		throw new RuntimeException("Not implemented.");
//            }
//        } else {
//    		throw new RuntimeException("Not implemented.");
//        }
	}
	
	/**
	 * Maps a URI to its corresponding file.
	 * 
	 * @param uri The URI.
	 * @return The file; or null when not found.
	 */
	@Nullable public IFile getFile(URI uri) {
		Contract.requireNotNull("uri", uri);
		
		return asFile(getResource(uri));
	}
	
	/**
	 * Maps a URI to its corresponding folder.
	 * 
	 * @param uri The URI.
	 * @return The folder; or null when not found.
	 */
	@Nullable public IFolder getFolder(URI uri) {
		Contract.requireNotNull("uri", uri);
		
		return asFolder(getResource(uri));
	}
	
	/**
	 * Maps a URI to its corresponding project.
	 * 
	 * @param uri The URI.
	 * @return The project; or null when not found.
	 */
	@Nullable public IProject getProject(URI uri) {
		Contract.requireNotNull("uri", uri);
		
		return asProject(getResource(uri));
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
		
		return getUri(getFile(document));
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
		
		return getUri(getFile(input));		
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
	
	/**
	 * Gets the editor associated with the specified workbench part reference.
	 * 
	 * @param reference The reference.
	 * @return The editor.
	 */
	public ITextEditor getEditor(IWorkbenchPartReference reference) {
		IWorkbenchPart part = reference.getPart(true);
		if (part != null && part instanceof ITextEditor) {
			return (ITextEditor) part;
		} else {
			return null;
		}
	}

	/**
	 * Gets the document associated with the specified editor.
	 * 
	 * @param editor The editor.
	 * @return The document.
	 */
	public IDocument getDocument(ITextEditor editor) {
		return editor.getDocumentProvider()
				.getDocument(editor.getEditorInput());
	}

	/**
	 * Gets the source viewer associated with the specified editor.
	 * 
	 * @param editor The editor.
	 * @return The source viewer.
	 */
	public ISourceViewer getSourceViewer(IEditorPart editor) {
		if (editor == null) { return null; }

		return (ISourceViewer)editor.getAdapter(ITextOperationTarget.class);
	}
	
	/**
	 * Returns the specified {@link IResource} as a {@link IFile}, if possible.
	 * @param resource The resource.
	 * @return The file; or null.
	 */
	@Nullable public IFile asFile(@Nullable IResource resource) {
		return resource != null && resource.getType() == IResource.FILE ? (IFile)resource : null;
	}
	
	/**
	 * Returns the specified {@link IResource} as a {@link IFolder}, if possible.
	 * @param resource The resource.
	 * @return The folder; or null.
	 */
	@Nullable public IFolder asFolder(@Nullable IResource resource) {
		return resource != null && resource.getType() == IResource.FOLDER ? (IFolder)resource : null;
	}
	
	/**
	 * Returns the specified {@link IResource} as a {@link IProject}, if possible.
	 * @param resource The resource.
	 * @return The project; or null.
	 */
	@Nullable public IProject asProject(@Nullable IResource resource) {
		return resource != null && resource.getType() == IResource.PROJECT ? (IProject)resource : null;
	}

	@Override
	public URI getProjectOf(URI uri) {
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
		
		IFile file = asFile(getResource(uri));
		return file != null;
	}

	@Override
	public boolean isFolder(URI uri) {
		Contract.requireNotNull("uri", uri);
		
		IFolder folder = asFolder(getResource(uri));
		return folder != null;
	}

	@Override
	public boolean isProject(URI uri) {
		Contract.requireNotNull("uri", uri);
		
		IProject project = asProject(getResource(uri));
		return project != null;
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
		return new StringContent(document.get(), stamp);
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

	@Override
	@Nullable
	public URI getParent(URI uri) {
		// This is probably not correct in all situations.
		if (uri.getPath().endsWith("/"))
			return uri.resolve("..");
		else
			return uri.resolve(".");
	}

	@Override
	@Nullable
	public Long getOffset(IContent content, Position position) {
		if (content instanceof IAesiContent) {
			IAesiContent aesiContent = (IAesiContent)content;
			return aesiContent.getOffset(position);
		} else {
			return null;
		}
	}

	@Override
	@Nullable
	public Position getPosition(IContent content, long offset) {
		if (content instanceof IAesiContent) {
			IAesiContent aesiContent = (IAesiContent)content;
			return aesiContent.getPosition(offset);
		} else {
			return null;
		}
	}

}
