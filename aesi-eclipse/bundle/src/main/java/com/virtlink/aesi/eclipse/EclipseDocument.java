package com.virtlink.aesi.eclipse;

import com.virtlink.editorservices.IDocument;

public class EclipseDocument implements IDocument {

	private final org.eclipse.jface.text.IDocument document;
	
	public EclipseDocument(final org.eclipse.jface.text.IDocument document) {
		if (document == null)
			throw new IllegalArgumentException("document must not be null.");
		
		this.document = document;
	}
	
	@Override
	public String getText() {
		return this.document.get();
	}

}
