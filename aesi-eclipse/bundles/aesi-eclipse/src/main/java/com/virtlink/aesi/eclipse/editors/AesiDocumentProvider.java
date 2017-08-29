package com.virtlink.aesi.eclipse.editors;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentPartitioner;
import org.eclipse.jface.text.rules.FastPartitioner;
import org.eclipse.ui.editors.text.FileDocumentProvider;

public class AesiDocumentProvider extends FileDocumentProvider {

	@Override
	protected IDocument createDocument(Object element) throws CoreException {
		// The base method creates a new document if `element instanceof IEditorInput`
		// otherwise returns null.
		return super.createDocument(element);
	}

	@Override
	protected IDocument createEmptyDocument() {
		return new AesiDocument();
	}
}