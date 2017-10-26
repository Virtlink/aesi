package com.virtlink.editorservices.eclipse;

import java.net.URI;
import java.net.URISyntaxException;

import com.virtlink.editorservices.IDocument;

public class DummyDocument implements IDocument {

	private static final long serialVersionUID = -6839905949015782180L;

	@Override
	public URI getUri() {
		try {
			return new URI("dummy");
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}

}
