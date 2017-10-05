package com.virtlink.editorservices.eclipse;

import org.eclipse.jface.text.contentassist.IContentAssistProcessor;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import com.virtlink.aesi.eclipse.DocumentManager;
import com.virtlink.aesi.eclipse.ProjectManager;
import com.virtlink.aesi.eclipse.codecompletion.AesiCompletionProcessor;
import com.virtlink.aesi.eclipse.editors.AesiSourceViewerConfiguration;
import com.virtlink.aesi.eclipse.editors.ColorManager;
import com.virtlink.aesi.eclipse.editors.IContentAssistProcessorFactory;

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
		bind(ColorManager.class).toInstance(new ColorManager());
		bind(AesiSourceViewerConfiguration.class);
	}

}
