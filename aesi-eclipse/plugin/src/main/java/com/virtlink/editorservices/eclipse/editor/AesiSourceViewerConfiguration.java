package com.virtlink.editorservices.eclipse.editor;

import org.eclipse.jface.text.reconciler.IReconciler;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.ui.editors.text.TextSourceViewerConfiguration;

/**
 * Source viewer configuration for the AESI editor.
 */
public class AesiSourceViewerConfiguration extends TextSourceViewerConfiguration {

	@Override
	public IReconciler getReconciler(ISourceViewer sourceViewer) {
		// Reconciler disabled.
		return null;
	}
}
