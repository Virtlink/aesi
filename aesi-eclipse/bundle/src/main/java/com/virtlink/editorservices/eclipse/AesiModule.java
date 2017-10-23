package com.virtlink.editorservices.eclipse;

import com.virtlink.aesi.eclipse.editors.*;
import com.virtlink.aesi.eclipse.structureoutline.AesiContentProvider;
import com.virtlink.aesi.eclipse.structureoutline.AesiOutlinePage;
import com.virtlink.aesi.eclipse.structureoutline.IContentProviderFactory;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import com.virtlink.aesi.eclipse.DocumentManager;
import com.virtlink.aesi.eclipse.ProjectManager;
import com.virtlink.aesi.eclipse.codecompletion.AesiCompletionProcessor;

public class AesiModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(ProjectManager.class).in(Singleton.class);
		bind(DocumentManager.class).in(Singleton.class);
		bindCodeCompletion();
	}
	
	protected void bindCodeCompletion() {
		install(new FactoryModuleBuilder()
				.implement(IContentAssistProcessor.class, AesiCompletionProcessor.class)
				.build(IContentAssistProcessorFactory.class));
		install(new FactoryModuleBuilder()
				.implement(AesiSourceScanner.class, AesiSourceScanner.class)
				.build(ISourceScannerFactory.class));
		install(new FactoryModuleBuilder()
				.implement(AesiContentProvider.class, AesiContentProvider.class)
				.build(IContentProviderFactory.class));
		install(new FactoryModuleBuilder()
				.implement(AesiOutlinePage.class, AesiOutlinePage.class)
				.build(IOutlinePageFactory.class));



		bind(ColorManager.class).toInstance(new ColorManager());
		bind(AesiSourceViewerConfiguration.class);
	}

}
