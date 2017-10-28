package com.virtlink.editorservices.eclipse.actions;

import org.eclipse.osgi.util.NLS;

/**
 * The strings used in actions.
 * 
 * These are read from the resource:
 * com/virtlink/editorservices/actions/ActionMessages.properties
 */
public final class ActionMessages extends NLS {
	private static final String BUNDLE_NAME= "com.virtlink.editorservices.actions.ActionMessages"; //$NON-NLS-1$
	static { NLS.initializeMessages(BUNDLE_NAME, ActionMessages.class); }
	private ActionMessages() {}
	
	// These static fields must match those in the ActionMessages.properties resource.
	public static String OpenDeclaration_label;
	public static String OpenDeclaration_description;
}
