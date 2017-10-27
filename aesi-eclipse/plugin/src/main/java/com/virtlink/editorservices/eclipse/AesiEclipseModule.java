package com.virtlink.editorservices.eclipse;

import org.eclipse.jface.text.source.SourceViewerConfiguration;
import org.eclipse.ui.texteditor.IDocumentProvider;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import com.virtlink.editorservices.documents.IResourceManager;
import com.virtlink.editorservices.eclipse.content.EclipseResourceManager;
import com.virtlink.editorservices.eclipse.editor.AesiDocumentProvider;
import com.virtlink.editorservices.eclipse.editor.AesiSourceViewerConfiguration;
import com.virtlink.editorservices.eclipse.editor.ColorizationJob;
import com.virtlink.editorservices.eclipse.syntaxcoloring.AesiColorManager;
import com.virtlink.editorservices.eclipse.syntaxcoloring.PresentationMerger;
import com.virtlink.editorservices.eclipse.syntaxcoloring.StyleManager;

public class AesiEclipseModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(PresentationMerger.class).in(Singleton.class);
		bind(EclipseResourceManager.class).in(Singleton.class);
		bind(StyleManager.class).in(Singleton.class);
		bind(IResourceManager.class).to(EclipseResourceManager.class);
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
		requestStaticInjection(AesiConstants.class);
	}
	
	protected void configureEditor() {
		bind(IDocumentProvider.class).to(AesiDocumentProvider.class);
		bind(SourceViewerConfiguration.class).to(AesiSourceViewerConfiguration.class);
	}

}
