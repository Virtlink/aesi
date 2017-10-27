package com.virtlink.editorservices.eclipse.editor;

import org.eclipse.jface.text.reconciler.IReconciler;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.editors.text.TextSourceViewerConfiguration;
import org.eclipse.ui.texteditor.ITextEditor;

import com.virtlink.editorservices.eclipse.Contract;

/**
 * Source viewer configuration that is editor aware.
 */
public class EditorAwareSourceViewerConfiguration extends TextSourceViewerConfiguration {
	
	private final ITextEditor editor;
	
	/**
	 * Initializes a new instance of this class.
	 * 
	 * @param editor The editor.
	 */
	public EditorAwareSourceViewerConfiguration(ITextEditor editor) {
		Contract.requireNotNull("editor", editor);
		
		this.editor = editor;
	}

	@Override
	public IReconciler getReconciler(ISourceViewer sourceViewer) {
		
		// Reconciler disabled.
		return null;
	}
}
