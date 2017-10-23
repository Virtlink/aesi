package com.virtlink.paplj;

import com.google.inject.assistedinject.FactoryModuleBuilder;
import com.virtlink.aesi.eclipse.editors.AesiSourceViewerConfiguration;
import com.virtlink.aesi.eclipse.editors.ColorManager;
import com.virtlink.aesi.eclipse.editors.IContentAssistProcessorFactory;
import com.virtlink.editorservices.codecompletion.ICodeCompleter;
import com.virtlink.editorservices.eclipse.AesiModule;
import com.virtlink.paplj.codecompletion.DummyCodeCompleter;

public class PapljModule extends AesiModule {
	@Override
	protected void bindCodeCompletion() {
		bind(ICodeCompleter.class).to(DummyCodeCompleter.class);
		// TODO: Add other dependencies
		super.bindCodeCompletion();
	}
}
