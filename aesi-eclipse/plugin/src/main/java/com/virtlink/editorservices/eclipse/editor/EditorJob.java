package com.virtlink.editorservices.eclipse.editor;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.ui.IEditorInput;

import com.virtlink.editorservices.eclipse.Contract;

/**
 * An editor job.
 */
public abstract class EditorJob extends Job {
	
	private final IEditorInput input;
	
	/**
	 * Gets the editor input.
	 * 
	 * @return The editor input.
	 */
	protected IEditorInput getInput() {
		return this.input;
	}

	/**
	 * Initializes a new instance of the {@link EditorJob} class.
	 * 
	 * @param description The job description.
	 * @param input The editor input.
	 */
	public EditorJob(String description, IEditorInput input) {
		super(description);
		Contract.requireNotNull("input", input);
		
		this.input = input;
	}
	
	@Override
	public boolean belongsTo(Object family) {
		// A job belongs to its input
		// and to its own class.
		return this.input.equals(family);
	}

	@Override
	protected abstract IStatus run(IProgressMonitor monitor);

}
