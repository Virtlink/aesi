package com.virtlink.editorservices.eclipse;

import org.eclipse.jface.text.rules.ITokenScanner;
import org.eclipse.jface.text.source.SourceViewerConfiguration;
import org.eclipse.ui.texteditor.IDocumentProvider;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import com.virtlink.editorservices.eclipse.editor.AesiDocumentProvider;
import com.virtlink.editorservices.eclipse.editor.AesiSourceViewerConfiguration;
import com.virtlink.editorservices.eclipse.editor.AesiSwtResourceManager;
import com.virtlink.editorservices.eclipse.editor.ColorizationJob;
import com.virtlink.editorservices.eclipse.syntaxcoloring.AesiColorManager;
import com.virtlink.editorservices.eclipse.syntaxcoloring.AesiSourceScanner;
import com.virtlink.editorservices.eclipse.syntaxcoloring.PresentationMerger;

public class AesiEclipseModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(ITokenScanner.class).to(AesiSourceScanner.class).in(Singleton.class);		
		bind(AesiSwtResourceManager.class).in(Singleton.class);
		bind(PresentationMerger.class).in(Singleton.class);
		bind(AesiColorManager.class).in(Singleton.class);
		
		install(new FactoryModuleBuilder()
			     .implement(ColorizationJob.class, ColorizationJob.class)
			     .build(ColorizationJob.IFactory.class));

		injectStatics();
	}
	
	/**
	 * These classes are not instantiated by the Guice injector,
	 * so we need a different way to get our dependencies injected.
	 * Here we use static injection.
	 */
	protected void injectStatics() {
	}
	
	protected void configureEditor() {
		bind(IDocumentProvider.class).to(AesiDocumentProvider.class);
		bind(SourceViewerConfiguration.class).to(AesiSourceViewerConfiguration.class);
	}

}
