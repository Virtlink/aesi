package com.virtlink.editorservices.eclipse.syntaxcoloring;

import static com.virtlink.editorservices.eclipse.Contract.requireNotNull;

import org.eclipse.jface.resource.ColorRegistry;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.ui.services.IDisposable;

/**
 * Manages the {@link Color} objects.
 */
public final class ColorManager implements IDisposable {
	
	private final ColorRegistry colorRegistry;
	
	/**
	 * Initializes a new instance of the {@link ColorManager} class.
	 * @param colorRegistry The color registry to use.
	 */
	public ColorManager(ColorRegistry colorRegistry) {
		requireNotNull("colorRegistry", colorRegistry);
		
		this.colorRegistry = colorRegistry;
	}

	/**
	 * Initializes a new instance of the {@link ColorManager} class.
	 */
	public ColorManager() {
		this(JFaceResources.getColorRegistry());
	}
	
	/**
	 * Gets a {@link Color} object with the specified RGB color values.
	 * @param rgb The RGB color values.
	 * @return The color object.
	 */
	public Color getColor(RGB rgb) {
		String symbolicName = rgb.toString();
		this.colorRegistry.put(symbolicName, rgb);
		return this.colorRegistry.get(symbolicName);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void dispose() {
		// Nothing to do.
	}
}
