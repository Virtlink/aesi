package com.virtlink.editorservices.eclipse.editor;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.ui.IEditorInput;

import com.virtlink.editorservices.eclipse.Contract;

/**
 * An editor job.
 */
public abstract class EditorJob extends Job {
	
	/** The editor. */
	private final IAesiEditor editor;
	
	/**
	 * Gets the editor.
	 * 
	 * @return The editor.
	 */
	protected IAesiEditor getEditor() {
		return this.editor;
	}
	
	/**
	 * Gets the editor input.
	 * 
	 * @return The editor input.
	 */
	protected IEditorInput getInput() {
		return this.editor.getEditorInput();
	}

	/**
	 * Initializes a new instance of the {@link EditorJob} class.
	 * 
	 * @param description The job description.
	 * @param editor The editor.
	 */
	public EditorJob(final String description, final IAesiEditor editor) {
		super(description);
		Contract.requireNotNull("editor", editor);

		this.editor = editor;
	}
	
	@Override
	public boolean belongsTo(Object family) {
		// A job belongs to its input
		// and to its own class.
		return this.getInput().equals(family);
	}

	@Override
	protected abstract IStatus run(IProgressMonitor monitor);

}
