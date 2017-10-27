package com.virtlink.editorservices.eclipse.editor;

import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.services.IDisposable;

import javax.annotation.Nullable;

import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.resource.LocalResourceManager;

/**
 * Manages SWT resources for a particular display.
 */
public class AesiSwtResourceManager implements IDisposable {

	@Nullable
	private ResourceManager innerManager;
	
	/**
	 * Initializes a new instance of the class.
	 * @param display The display.
	 */
	public AesiSwtResourceManager(Display display) {
		innerManager = new LocalResourceManager(JFaceResources.getResources(display));
	}

	@Override
	public void dispose() {
		if (this.innerManager != null) {
			this.innerManager.dispose();
		}
		this.innerManager = null;
	}
}
