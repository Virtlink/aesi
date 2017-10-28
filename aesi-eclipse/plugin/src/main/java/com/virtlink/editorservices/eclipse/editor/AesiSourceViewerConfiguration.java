package com.virtlink.editorservices.eclipse.editor;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.contentassist.ContentAssistant;
import org.eclipse.jface.text.contentassist.IContentAssistant;
import org.eclipse.jface.text.reconciler.IReconciler;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.ui.editors.text.TextSourceViewerConfiguration;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.virtlink.editorservices.eclipse.Contract;
import com.virtlink.editorservices.eclipse.codecompletion.AesiCompletionProcessor;

/**
 * Source viewer configuration for the AESI editor.
 */
public class AesiSourceViewerConfiguration extends TextSourceViewerConfiguration {

	/**
	 * Factory for the {@link AesiSourceViewerConfiguration} class.
	 */
	public static interface IFactory {
		/**
		 * Creates a new instance of the {@link AesiSourceViewerConfiguration}.
		 * 
	     * @param editor The editor.
		 * @return The source viewer configuration.
		 */
		AesiSourceViewerConfiguration create(IAesiEditor editor);
	}
	
	private final IAesiEditor editor;
	private final AesiCompletionProcessor.IFactory completionProcessorFactory;
	
	@Inject public AesiSourceViewerConfiguration(
			@Assisted final IAesiEditor editor,
			final AesiCompletionProcessor.IFactory completionProcessorFactory
	) {
		Contract.requireNotNull("editor", editor);
		Contract.requireNotNull("completionProcessorFactory", completionProcessorFactory);
		
		this.editor = editor;
		this.completionProcessorFactory = completionProcessorFactory;
	}
	
	@Override
	public IReconciler getReconciler(ISourceViewer sourceViewer) {
		// Reconciler disabled.
		return null;
	}
	
	@Override
	public IContentAssistant getContentAssistant(ISourceViewer sourceViewer) {
		ContentAssistant assistant = new ContentAssistant();
		assistant.setContextInformationPopupOrientation(IContentAssistant.CONTEXT_INFO_ABOVE);
		
		AesiCompletionProcessor processor = this.completionProcessorFactory.create(editor);
		assistant.setContentAssistProcessor(processor, IDocument.DEFAULT_CONTENT_TYPE);
		assistant.install(sourceViewer);
		
		return assistant;
	}
}
