package com.virtlink.aesi.eclipse;

import org.eclipse.core.runtime.Status;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import javax.annotation.Nullable;

/**
 * The activator class controls the plug-in life cycle
 */
public class AesiPlugin extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "com.virtlink.aesi.eclipse"; //$NON-NLS-1$

	// The shared instance
	@Nullable
	private static AesiPlugin plugin;
	
	/**
	 * The constructor
	 */
	public AesiPlugin() {
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
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	@Nullable
	public static AesiPlugin getDefault() {
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
		@Nullable AesiPlugin plugin = getDefault();
		return plugin != null ? plugin.getBundle().getSymbolicName() : "com.virtlink.aesi.eclipse.debugging";
	}
}
