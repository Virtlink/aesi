package com.virtlink.aesi.eclipse.editors;

import com.virtlink.aesi.eclipse.structureoutline.AesiOutlinePage;

import javax.annotation.Nullable;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;

/**
 * Editor for AESI languages.
 */
public abstract class AesiEditor extends TextEditor {

	private ColorManager colorManager;
	@Nullable private AesiOutlinePage outlinePage;
	private final IOutlinePageFactory outlinePageFactory;

	public AesiEditor(
			ColorManager colorManager,
			AesiSourceViewerConfiguration sourceViewerConfiguration,
			IOutlinePageFactory outlinePageFactory
	) {
		super();
		this.colorManager = colorManager;
		this.outlinePageFactory = outlinePageFactory;

		setSourceViewerConfiguration(sourceViewerConfiguration);
//		this.colorManager = new ColorManager();
//
//		setSourceViewerConfiguration(new AesiSourceViewerConfiguration(colorManager));
		setDocumentProvider(new AesiDocumentProvider());
	}

	@Override
	public Object getAdapter(Class requested) {
		if (!IContentOutlinePage.class.equals(requested))
			return super.getAdapter(requested);

		if (this.outlinePage == null) {
			this.outlinePage = outlinePageFactory.create(this.getDocumentProvider(), this);
			@Nullable IEditorInput input = this.getEditorInput();
			if (input != null)
				this.outlinePage.setInput(input);
		}

		return this.outlinePage;
	}

	@Override
	protected void doSetInput(IEditorInput input) throws CoreException {
		super.doSetInput(input);
		if (this.outlinePage != null)
			this.outlinePage.setInput(input);
	}
	
	@Override
	public void doRevertToSaved() {
		super.doRevertToSaved();
		update();
	}
	
	@Override
	public void doSave(IProgressMonitor progressMonitor) {
		super.doSave(progressMonitor);
		update();
	}
	
	@Override
	public void doSaveAs() {
		super.doSaveAs();
		update();
	}
	
	@Override
	protected void editorSaved() {
		super.editorSaved();
		update();
	}
	
	protected void update() {
		if (this.outlinePage != null)
			this.outlinePage.update();
	}
	
	@Override
	public void dispose() {
		colorManager.dispose();
		super.dispose();
	}

}
