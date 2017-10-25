package com.virtlink.editorservices.eclipse;

import org.eclipse.debug.internal.ui.ColorManager;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.virtlink.editorservices.eclipse.syntaxcoloring.AesiReconciler;
import com.virtlink.editorservices.eclipse.syntaxcoloring.AesiSourceScanner;

public class AesiEclipseModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(AesiSourceScanner.class).in(Singleton.class);		
		bind(ColorManager.class).in(Singleton.class);
		// ISyntaxColoringService
		
		injectStatics();
	}
	
	/**
	 * These classes are not instantiated by the Guice injector,
	 * so we need a different way to get our dependencies injected.
	 * Here we use static injection.
	 */
	protected void injectStatics() {
		requestStaticInjection(AesiReconciler.class);
	}

}
