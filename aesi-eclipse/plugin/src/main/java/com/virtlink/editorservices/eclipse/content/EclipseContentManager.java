package com.virtlink.editorservices.eclipse.content;

import com.virtlink.editorservices.IDocument;
import com.virtlink.editorservices.content.IContent;
import com.virtlink.editorservices.content.IContentManager;
import com.virtlink.editorservices.content.StringContent;

public class EclipseContentManager implements IContentManager {

	@Override
	public IContent getLatestContent(IDocument document) {
		// TODO Get text
		String text = null;
		return new StringContent(text);
	}

}
