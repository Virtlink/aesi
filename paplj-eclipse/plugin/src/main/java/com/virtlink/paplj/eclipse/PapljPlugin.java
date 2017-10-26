package com.virtlink.paplj.eclipse;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import com.google.inject.Guice;
import com.google.inject.Injector;

import javax.annotation.Nullable;

/**
 * The activator class controls the plug-in life cycle
 */
public class PapljPlugin extends AbstractUIPlugin {

	/** The plug-in ID. */
	public static final String PLUGIN_ID = "aesi-eclipse"; //$NON-NLS-1$

	/** The shared plugin instance. */
	@Nullable
	private static PapljPlugin plugin;
	
	/** The dependency injector. */
	private Injector injector;
	
	/**
	 * Gets the dependency injector.
	 * 
	 * @return The dependency injector.
	 */
	public Injector getInjector() {
		return this.injector;
	}
	
	/**
	 * The constructor
	 */
	public PapljPlugin() {
		this.injector = Guice.createInjector(new PapljAesiModule());
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared plugin instance.
	 *
	 * @return The shared instance.
	 */
	public static PapljPlugin getDefault() {
		return plugin;
	}

	/**
	 * Returns an image descriptor for the image file at the given
	 * plug-in relative path.
	 *
	 * @param path The path.
	 * @return The image descriptor.
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}
}
