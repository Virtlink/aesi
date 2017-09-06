package com.virtlink.editorservices;

import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import com.virtlink.aesi.eclipse.editors.AesiSourceViewerConfiguration;
import com.virtlink.aesi.eclipse.editors.ColorManager;
import com.virtlink.aesi.eclipse.editors.IContentAssistProcessorFactory;

public class AesiModule extends AbstractModule {

	@Override
	protected void configure() {
		bindCodeCompletion();
		
	}
	
	protected void bindCodeCompletion() {
		install(new FactoryModuleBuilder().build(IContentAssistProcessorFactory.class));
		bind(ColorManager.class).toInstance(new ColorManager());
		bind(AesiSourceViewerConfiguration.class);
	}

}
