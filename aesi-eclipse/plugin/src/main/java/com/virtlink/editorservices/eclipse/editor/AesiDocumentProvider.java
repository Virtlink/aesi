package com.virtlink.editorservices.eclipse.editor;

import java.util.Iterator;

import javax.annotation.Nullable;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.text.IDocument;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.editors.text.TextFileDocumentProvider;
import org.eclipse.ui.texteditor.DocumentProviderRegistry;

import com.virtlink.editorservices.eclipse.AesiConstants;

/**
 * Creates the Eclipse documents for the AESI editor.
 */
public class AesiDocumentProvider extends TextFileDocumentProvider {
	
	public static AesiDocumentProvider getInstance() {
		return (AesiDocumentProvider)DocumentProviderRegistry.getDefault()
				.getDocumentProvider(AesiConstants.getFileExtension());
	}

	public AesiDocumentProvider() 
	{
	}
	
	/**
	 * Attempts to find the file of a document
	 * by looking through all elements connected to this
	 * document provider and checking whether they use the
	 * given document. Do not use in performance-critical
	 * applications.
	 * 
	 * @param document The document whose file to find.
	 * @return The file; or null.
	 */
	@Nullable
	public IFile getFile(final IDocument document) {
		for (final Iterator<Object> iterator = getConnectedElementsIterator(); iterator.hasNext();) {
			final Object element = iterator.next();
			if (getDocument(element) == document) {
				if (element instanceof IFileEditorInput) {
					return ((IFileEditorInput)element).getFile();
				} else {
					final IPath location = getFileInfo(element).fTextFileBuffer.getLocation();
					if (location != null) {
						return (IFile)ResourcesPlugin.getWorkspace().getRoot().findMember(location);
					}
				}
			}
		}
		return null;
	}
}
