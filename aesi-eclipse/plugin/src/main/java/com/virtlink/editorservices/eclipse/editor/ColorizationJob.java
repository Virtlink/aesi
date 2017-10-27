package com.virtlink.editorservices.eclipse.editor;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.text.TextPresentation;
import org.eclipse.ui.IEditorInput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.assistedinject.Assisted;
import com.virtlink.editorservices.ICancellationToken;
import com.virtlink.editorservices.eclipse.Contract;
import com.virtlink.editorservices.eclipse.MonitorCancellationToken;
import com.virtlink.editorservices.syntaxcoloring.ISyntaxColoringService;

/**
 * Colorization job.
 */
public class ColorizationJob extends EditorJob {
	
	/**
	 * Factory for the {@link ColorizationJob} class.
	 */
	public static interface IFactory {
		/**
		 * Creates a new instance of the {@link ColorizationJob}.
		 * 
		 * @param input The editor input.
		 * @return The job.
		 */
		ColorizationJob create(IEditorInput input);
	}

	/** The logger. */
	private static Logger LOG = LoggerFactory.getLogger(AesiEditor.class);
	/** The syntax coloring service. */
	private final ISyntaxColoringService syntaxColoringService;
	
	/**
	 * Initializes a new instance of the {@link ColorizationJob} class.
	 * 
	 * @param input The editor input.
	 * @param syntaxColoringService The syntax coloring service.
	 */
	public ColorizationJob(
			@Assisted final IEditorInput input,
			final ISyntaxColoringService syntaxColoringService
	) {
		super(getDescription(input), input);
		Contract.requireNotNull("syntaxColoringService", syntaxColoringService);
		
		this.syntaxColoringService = syntaxColoringService;
	}
	
	/**
	 * Gets the description for this job.
	 * 
	 * @param input The editor input.
	 * @return The description.
	 */
	private static String getDescription(final IEditorInput input) {
		Contract.requireNotNull("input", input);
		return "Colorizing " + input.getName();
	}

	@Override
	protected IStatus run(IProgressMonitor monitor) {
		
		IProject project;
		IDocument document;
		Span region;
		ICancellationToken ct = new MonitorCancellationToken(monitor);
		this.syntaxColoringService.getTokens(arg0, arg1, arg2, ct);
		
		// TODO Auto-generated method stub
		return null;
//		final TextPresentation textPresentation = StyleUtils.createTextPresentation(style, display);
	}

}
