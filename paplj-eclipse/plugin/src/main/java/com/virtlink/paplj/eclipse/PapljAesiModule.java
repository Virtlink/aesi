package com.virtlink.paplj.eclipse;

import com.google.inject.AbstractModule;
import com.virtlink.paplj.PapljModule;
import com.google.inject.Singleton;
import com.virtlink.editorservices.codecompletion.ICodeCompletionService;
import com.virtlink.editorservices.eclipse.AesiEclipseModule;
import com.virtlink.editorservices.eclipse.FileExtension;
import com.virtlink.editorservices.referenceresolution.IReferenceResolverService;
import com.virtlink.editorservices.structureoutline.IStructureOutlineService;
import com.virtlink.editorservices.syntaxcoloring.ISyntaxColoringService;
import com.virtlink.paplj.codecompletion.DummyCodeCompleter;
import com.virtlink.paplj.referenceresolution.DummyReferenceResolver;
import com.virtlink.paplj.structureoutline.DummyStructureOutliner;
import com.virtlink.paplj.syntaxcoloring.AntlrSyntaxColorizer;

public class PapljAesiModule extends AbstractModule {

	@Override
	protected void configure() {
		install(new PapljModule());
		install(new AesiEclipseModule());
		
		bindConstant().annotatedWith(FileExtension.class).to("pj");
	}

}
