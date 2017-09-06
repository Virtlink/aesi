package com.virtlink.paplj.eclipse;

import org.eclipse.core.runtime.Status;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.virtlink.paplj.PapljModule;

import javax.annotation.Nullable;

/**
 * The activator class controls the plug-in life cycle
 */
public class PapljPlugin extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "com.virtlink.aesi.eclipse"; //$NON-NLS-1$

	// The shared instance
	@Nullable
	private static PapljPlugin plugin;
	
	// The dependency injector.
	private static Injector injector;
	
	/**
	 * Gets the dependency injector.
	 * @return The dependency injecotr.
	 */
	public static Injector getInjector() { return PapljPlugin.injector; }
	
	/**
	 * The constructor
	 */
	public PapljPlugin() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		PapljPlugin.injector = Guice.createInjector(new PapljModule());
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
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	@Nullable
	public static PapljPlugin getDefault() {
		return plugin;
	}

	/**
	 * Returns an image descriptor for the image file at the given
	 * plug-in relative path
	 *
	 * @param path the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}

	public static String getDebugModelIdentifier()
	{
		@Nullable PapljPlugin plugin = getDefault();
		return plugin != null ? plugin.getBundle().getSymbolicName() : "com.virtlink.paplj.eclipse.debugging";
	}
}
