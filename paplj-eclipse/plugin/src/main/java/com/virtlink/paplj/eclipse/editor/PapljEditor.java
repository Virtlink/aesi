package com.virtlink.paplj.eclipse.editor;

import org.eclipse.jface.text.source.SourceViewerConfiguration;
import org.eclipse.ui.texteditor.IDocumentProvider;

import com.virtlink.editorservices.eclipse.editor.AesiEditor;
import com.virtlink.paplj.eclipse.PapljPlugin;

/**
 * Editor for the PAPLJ language.
 */
public class PapljEditor extends AesiEditor {

	public PapljEditor(SourceViewerConfiguration sourceViewerConfiguration, IDocumentProvider documentProvider) {
		super(
				PapljPlugin.getDefault().getInjector().getInstance(SourceViewerConfiguration.class),
				PapljPlugin.getDefault().getInjector().getInstance(IDocumentProvider.class)
		);
	}

}
