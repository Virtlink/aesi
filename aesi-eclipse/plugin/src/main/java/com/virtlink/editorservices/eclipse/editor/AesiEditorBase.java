package com.virtlink.editorservices.eclipse.editor;


import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.TextPresentation;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.texteditor.AbstractTextEditor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.virtlink.editorservices.eclipse.Contract;
import com.virtlink.editorservices.eclipse.syntaxcoloring.PresentationMerger;

/**
 * AESI editor base.
 */
public abstract class AesiEditorBase extends AbstractTextEditor {

	/** The logger. */
	private static Logger LOG = LoggerFactory.getLogger(AesiEditorBase.class);
	/** Merges our presentation with any changes from others. */
	private final PresentationMerger presentationMerger;
	
	@Inject
	protected AesiEditorBase(
			final PresentationMerger presentationMerger
	) {
		Contract.requireNotNull("presentationMerger", presentationMerger);
		
		this.presentationMerger = presentationMerger;
	}
	
	/**
	 * Invokes the specified function on the UI thread.
	 * 
	 * @param runnable The function to execute.
	 */
	protected void asyncUI(Runnable runnable) {
		Display.getDefault().asyncExec(runnable);
	}
	
	/**
	 * Gets the current document in the editor.
	 * 
	 * @return The current document.
	 */
	public IDocument getDocument() {
		return getDocumentProvider().getDocument(getEditorInput());
	}
	
	@Override
	protected void doSetInput(IEditorInput input) throws CoreException {
		
		
		handleEditorInputChanging();

		
		cancelAllJobs();
		
		super.doSetInput(input);
		
		configureDocument();
	}
	
	/**
	 * Configures the editor with the current editor input and document.
	 */
	private void configureDocument() {
		handleEditorInputChanged();
	}
	
	/**
	 * Handles the editor input changing.
	 */
	protected void handleEditorInputChanging() {
		// Nothing to do by default.
	}
	
	/**
	 * Handles the editor input having changed.
	 */
	protected void handleEditorInputChanged() {
		// Nothing to do by default.
	}
	
	/**
	 * Cancels all running jobs for the current editor input.
	 */
	protected void cancelAllJobs() {
		cancelAllJobs(getEditorInput());
	}
	
	/**
	 * Cancels all running jobs for the specified family.
	 * 
	 * @param family The job family.
	 */
	protected void cancelAllJobs(Object family) {
		LOG.info("Cancelling all jobs for {}", family);
		final Job[] runningJobs = Job.getJobManager().find(family);
		for (Job job : runningJobs) {
			job.cancel();
		}
	}
	
	/**
	 * Sets the editor text presentation.
	 * 
	 * @param presentation The text presentation.
	 * @param text The text that was presented. (Used to check whether the presentation is still valid.)
	 * @param monitor The progress monitor.
	 */
	protected void setPresentation(final TextPresentation presentation, final String text, final IProgressMonitor monitor) {
		this.presentationMerger.set(presentation);
		
		asyncUI(() -> {
			if(monitor.isCanceled()) {
				// Operation was cancelled.
                return;
			}
			IDocument document = getDocument();
			if(document == null || !document.get().equals(text)) {
				// The document has changed, so the colorization is no longer valid.
                return;
            }
			
			getSourceViewer().changeTextPresentation(presentation, true);
		});
	}
	
	/**
	 * Unsets the editor text presentation.
	 */
	protected void unsetPresentation() {
		this.presentationMerger.unset();
	}
	
}
