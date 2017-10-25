package com.virtlink.aesi.eclipse;

import com.virtlink.editorservices.IDocument;

public class DocumentManager {
	public IDocument getDocument(org.eclipse.jface.text.IDocument document) {
		return new EclipseDocument(document);
	}
}
