package com.virtlink.editorservices.eclipse.editor;

import javax.annotation.Nullable;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.jobs.IJobManager;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.text.DocumentEvent;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentListener;
import org.eclipse.jface.text.TextPresentation;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.IVerticalRuler;
import org.eclipse.jface.text.source.SourceViewerConfiguration;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.texteditor.AbstractTextEditor;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.virtlink.editorservices.eclipse.Contract;
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
	/** The current document listener listening for changes to the current document. */
	@Nullable private DocumentChangeListener documentChangeListener = null;
	/** The current colorization job, if any. */
	@Nullable private ColorizationJob currentColorizationJob = null;
	
	@Inject
	public AesiEditor(
			SourceViewerConfiguration sourceViewerConfiguration,
			IDocumentProvider documentProvider,
			PresentationMerger presentationMerger,
			ColorizationJob.IFactory colorizationJobFactory
	) {
		super(presentationMerger);
		Contract.requireNotNull("sourceViewerConfiguration", sourceViewerConfiguration);
		Contract.requireNotNull("documentProvider", documentProvider);
		Contract.requireNotNull("colorizationJobFactory", colorizationJobFactory);
		
		this.colorizationJobFactory = colorizationJobFactory;
	    
		setSourceViewerConfiguration(sourceViewerConfiguration);
	    setDocumentProvider(documentProvider);
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
        
        IResource resource;
        
        final Job job = this.colorizationJobFactory.create(getEditorInput());
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
