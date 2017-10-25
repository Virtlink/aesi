package com.virtlink.aesi.eclipse;

import com.virtlink.editorservices.IDocument;
import org.jetbrains.annotations.NotNull;

import java.net.URI;

public class EclipseDocument implements IDocument {

	private final URI uri;
	private final org.eclipse.jface.text.IDocument document;
	
	public EclipseDocument(final URI uri, final org.eclipse.jface.text.IDocument document) {
		if (document == null)
			throw new IllegalArgumentException("document must not be null.");

		this.uri = uri;
		this.document = document;
	}

	@NotNull
	@Override
	public URI getUri() {
		return this.uri;
	}
	
//	@Override
//	public String getText() {
//		return this.document.get();
//	}
}
