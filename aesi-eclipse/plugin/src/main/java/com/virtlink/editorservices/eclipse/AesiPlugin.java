package com.virtlink.editorservices.eclipse;

import com.google.inject.Injector;
import com.virtlink.editorservices.SessionManager;

/**
 * The plugin.
 */
public class AesiPlugin {
	
	private final Injector injector;
	
	public AesiPlugin(final Injector injector) {
		this.injector = injector;
	}
	
	/**
	 * Gets the dependency injector.
	 * 
	 * @return The injector.
	 */
	public Injector getInjector() {
		return this.injector;
	}

	/**
	 * Gets the session manager.
	 * 
	 * @return The session manager.
	 */
	public SessionManager getSessionManager() {
		return this.getInjector().getInstance(SessionManager.class);
	}
	
	/**
	 * Starts the plugin.
	 */
	public void start() {
		this.getSessionManager().start();
	}
	
	/**
	 * Stops the plugin.
	 */
	public void stop() {
		this.getSessionManager().stop();
	}
	
}
