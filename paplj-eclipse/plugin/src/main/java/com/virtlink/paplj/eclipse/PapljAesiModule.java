package com.virtlink.paplj.eclipse;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.virtlink.editorservices.eclipse.AesiEclipseModule;
import com.virtlink.editorservices.syntaxcoloring.ISyntaxColoringService;
import com.virtlink.paplj.syntaxcoloring.AntlrSyntaxColorizer;

public class PapljAesiModule extends AbstractModule {

	@Override
	protected void configure() {
		install(new AesiEclipseModule());
		
		bind(ISyntaxColoringService.class).to(AntlrSyntaxColorizer.class).in(Singleton.class);
	}

}
