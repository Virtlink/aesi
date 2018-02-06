package com.virtlink.editorservices.eclipse.editor;

import java.net.URI;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.text.TextPresentation;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.widgets.Display;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.virtlink.editorservices.ICancellationToken;
import com.virtlink.editorservices.Span;
import com.virtlink.editorservices.resources.IContent;
import com.virtlink.editorservices.eclipse.Contract;
import com.virtlink.editorservices.eclipse.MonitorCancellationToken;
import com.virtlink.editorservices.eclipse.SpanUtils;
import com.virtlink.editorservices.eclipse.resources.EclipseResourceManager;
import com.virtlink.editorservices.eclipse.syntaxcoloring.StyleManager;
import com.virtlink.editorservices.syntaxcoloring.ISyntaxColoringService;
import com.virtlink.editorservices.syntaxcoloring.IToken;

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
	     * @param editor The editor.
		 * @return The job.
		 */
		ColorizationJob create(IAesiEditor editor);
	}

	/** The logger. */
	private static Logger LOG = LoggerFactory.getLogger(AesiEditor.class);
	/** The syntax coloring service. */
	private final ISyntaxColoringService syntaxColoringService;
	/** The resource manager. */
	private final EclipseResourceManager resourceManager;
	/** The style manager. */
	private final StyleManager styleManager;
	
	/**
	 * Initializes a new instance of the {@link ColorizationJob} class.
	 * 
	 * @param editor The editor.
	 * @param syntaxColoringService The syntax coloring service.
	 * @param resourceManager The resource manager.
	 * @param styleManager The style manager.
	 */
	@Inject
	public ColorizationJob(
			@Assisted final IAesiEditor editor,
			final ISyntaxColoringService syntaxColoringService,
			final EclipseResourceManager resourceManager,
			final StyleManager styleManager
	) {
		super(getDescription(editor), editor);
		Contract.requireNotNull("syntaxColoringService", syntaxColoringService);
		Contract.requireNotNull("resourceManager", resourceManager);
		Contract.requireNotNull("styleManager", styleManager);
		
		this.syntaxColoringService = syntaxColoringService;
		this.resourceManager = resourceManager;
		this.styleManager = styleManager;
	}
	
	/**
	 * Gets the description for this job.
	 * 
	 * @param input The editor input.
	 * @return The description.
	 */
	private static String getDescription(final IAesiEditor editor) {
		Contract.requireNotNull("editor", editor);
		return "Colorizing " + editor.getEditorInput().getName();
	}

	@Override
	protected IStatus run(IProgressMonitor monitor) {
		
		colorize(monitor);
		
		if (monitor.isCanceled()) {
			return StatusUtils.cancel();
		}
		return StatusUtils.success();
	}
	
	private void colorize(IProgressMonitor monitor) {
		// FIXME: Do we need the IResource if we have the IEditorInput?
		final URI document = this.resourceManager.getUri(getInput());
		final IContent content = this.resourceManager.getContent(document);
		final Span region = Span.fromLength(0, (int)content.getLength());
		final ICancellationToken ct = new MonitorCancellationToken(monitor);
		final List<IToken> tokens = this.syntaxColoringService.getTokens(document, region, ct);
		
		final Display display = Display.getDefault();
		final TextPresentation presentation = createPresentation(tokens, display);
		
		this.getEditor().setPresentation(presentation, monitor);
	}
	
	private TextPresentation createPresentation(List<IToken> tokens, Display display) {
		final TextPresentation presentation = new TextPresentation();
        for(IToken token : tokens) {
            final StyleRange styleRange = this.styleManager.getStyleRange(token.getLocation(), token.getName());
            presentation.addStyleRange(styleRange);
        }
        Span extent = SpanUtils.fromRegion(presentation.getExtent());
        if(extent == null) {
            extent = Span.fromLength(0, 0);
        }
        
        final StyleRange defaultStyleRange = this.styleManager.getDefaultStyleRange(extent);
        presentation.setDefaultStyleRange(defaultStyleRange);

        return presentation;
	}
}
