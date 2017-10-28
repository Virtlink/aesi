package com.virtlink.editorservices.eclipse.editor;

import javax.annotation.Nullable;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.text.DocumentEvent;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentListener;
import org.eclipse.jface.text.source.SourceViewerConfiguration;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.virtlink.editorservices.eclipse.Contract;
import com.virtlink.editorservices.eclipse.content.EclipseResourceManager;
import com.virtlink.editorservices.eclipse.structureoutline.AesiOutlinePage;
import com.virtlink.editorservices.eclipse.syntaxcoloring.PresentationMerger;

/**
 * AESI editor.
 */
public class AesiEditor extends AesiEditorBase {

	/** Delay in the colorization update. */
	private static final int COLORIZATION_UPDATE_DELAY = 400; // ms
	/** The logger. */
	private static Logger LOG = LoggerFactory.getLogger(AesiEditor.class);
	/** The colorization job factory. */
	private final ColorizationJob.IFactory colorizationJobFactory;
	/** The resource manager. */
	private final EclipseResourceManager resourceManager;
	/** Outline page factory. */
	private final AesiOutlinePage.IFactory outlinePageFactory;
	/** The current document listener listening for changes to the current document. */
	@Nullable private DocumentChangeListener documentChangeListener = null;
	/** The current colorization job, if any. */
	@Nullable private ColorizationJob currentColorizationJob = null;
	/** The current outline page, if any. */
	@Nullable private AesiOutlinePage outlinePage;

	
	@Inject
	public AesiEditor(
			final AesiSourceViewerConfiguration.IFactory sourceViewerConfigurationFactory,
			final PresentationMerger presentationMerger,
			final ColorizationJob.IFactory colorizationJobFactory,
			final EclipseResourceManager resourceManager,
			final AesiOutlinePage.IFactory outlinePageFactory
	) {
		super(presentationMerger);
		Contract.requireNotNull("sourceViewerConfigurationFactory", sourceViewerConfigurationFactory);
		Contract.requireNotNull("presentationMerger", presentationMerger);
		Contract.requireNotNull("colorizationJobFactory", colorizationJobFactory);
		Contract.requireNotNull("resourceManager", resourceManager);
		Contract.requireNotNull("outlinePageFactory", outlinePageFactory);
		
		this.colorizationJobFactory = colorizationJobFactory;
		this.resourceManager = resourceManager;
		this.outlinePageFactory = outlinePageFactory;
	    
		final SourceViewerConfiguration sourceViewerConfiguration = sourceViewerConfigurationFactory.create(this);
		setSourceViewerConfiguration(sourceViewerConfiguration);
	    setDocumentProvider(AesiDocumentProvider.getInstance());
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getAdapter(Class<T> required) {
		if (IContentOutlinePage.class.equals(required)) {
			if (this.outlinePage == null) {
				this.outlinePage = this.outlinePageFactory.create(this.getDocumentProvider(), this);
				@Nullable IEditorInput input = this.getEditorInput();
				if (input != null)
					this.outlinePage.setInput(input);
			}
	
			return (T)this.outlinePage;
		} else {
			return super.getAdapter(required);
		}
	}
	
	@Override
	protected void handleEditorInputChanging() {
		final IDocument oldDocument = getDocument();
		if (this.documentChangeListener != null) {
			oldDocument.removeDocumentListener(this.documentChangeListener);
			this.documentChangeListener = null;
		}
	}
	
	@Override
	protected void handleEditorInputChanged() {
		final IDocument newDocument = getDocument();
		assert this.documentChangeListener == null : "The previous document listener was not removed.";
		this.documentChangeListener = new DocumentChangeListener();
		newDocument.addDocumentListener(this.documentChangeListener);

		if (this.outlinePage != null) {
			this.outlinePage.setInput(getEditorInput());
		}
		
		updateColorization(true, 0);
	}
	
	/**
	 * Updates the text colorization in the editor.
	 * 
	 * @param invalidate Whether to invalidate.
	 * @param delay The delay before updating the colorization, in ms.
	 */
	private void updateColorization(boolean invalidate, long delay) {
		if (this.currentColorizationJob != null) {
			this.currentColorizationJob.cancel();
			this.currentColorizationJob = null;
		}
		
        if(invalidate) {
        	unsetPresentation();
        }
        
        final IResource resource = this.resourceManager.getResource(getEditorInput());
        final Job job = this.colorizationJobFactory.create(this);
        job.setRule(JobUtils.ruleOf(resource));
        job.schedule(delay);
	}
	
	/**
	 * Listens to changes to the document in this editor.
	 */
	public final class DocumentChangeListener implements IDocumentListener {

		@Override
		public void documentAboutToBeChanged(DocumentEvent event) {
			// Nothing to do.
		}

		@Override
		public void documentChanged(DocumentEvent event) {
			updateColorization(true, COLORIZATION_UPDATE_DELAY);
		}

	}
	
}
