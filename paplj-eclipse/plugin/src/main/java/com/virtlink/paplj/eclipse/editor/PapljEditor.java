package com.virtlink.paplj.eclipse.editor;

import org.eclipse.jface.text.source.SourceViewerConfiguration;
import org.eclipse.ui.texteditor.IDocumentProvider;

import com.virtlink.editorservices.eclipse.content.EclipseResourceManager;
import com.virtlink.editorservices.eclipse.editor.AesiEditor;
import com.virtlink.editorservices.eclipse.editor.AesiSourceViewerConfiguration;
import com.virtlink.editorservices.eclipse.editor.ColorizationJob;
import com.virtlink.editorservices.eclipse.structureoutline.AesiOutlinePage;
import com.virtlink.editorservices.eclipse.syntaxcoloring.PresentationMerger;
import com.virtlink.paplj.eclipse.PapljPlugin;

/**
 * Editor for the PAPLJ language.
 */
public class PapljEditor extends AesiEditor {

	public PapljEditor() {
		super(
				PapljPlugin.getDefault().getInjector().getInstance(AesiSourceViewerConfiguration.IFactory.class),
				PapljPlugin.getDefault().getInjector().getInstance(PresentationMerger.class),
				PapljPlugin.getDefault().getInjector().getInstance(ColorizationJob.IFactory.class),
				PapljPlugin.getDefault().getInjector().getInstance(EclipseResourceManager.class),
				PapljPlugin.getDefault().getInjector().getInstance(AesiOutlinePage.IFactory.class)
		);
	}

}
