package com.virtlink.editorservices.eclipse;

import java.net.URI;
import java.net.URISyntaxException;

import com.virtlink.editorservices.IDocument;
import com.virtlink.editorservices.IProject;

public class DummyProject implements IProject {

	private static final long serialVersionUID = -7583455819136225569L;

	@Override
	public URI getUri() {
		try {
			return new URI("dummy");
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}

}
