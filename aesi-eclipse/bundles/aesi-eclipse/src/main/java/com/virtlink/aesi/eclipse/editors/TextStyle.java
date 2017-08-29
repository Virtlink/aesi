package com.virtlink.aesi.eclipse.editors;

import org.eclipse.jface.resource.ColorRegistry;
import org.eclipse.jface.text.TextAttribute;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import static com.virtlink.aesi.eclipse.Contract.*;

/**
 * Describes the style of source code.
 */
public final class TextStyle {

	private final RGB color;
	private final boolean isBold;
	private final boolean isItalic;
	private final boolean isUnderlined;
	private final boolean isStrickenthrough;
	
	/**
	 * Gets the color of the text.
	 * @return The color.
	 */
	public RGB getColor() { return this.color; }
	
	/**
	 * Gets whether the text is bold.
	 * @return True when the text is bold; otherwise, false.
	 */
	public boolean isBold() { return this.isBold; }

	/**
	 * Gets whether the text is italic.
	 * @return True when the text is italic; otherwise, false.
	 */
	public boolean isItalic() { return this.isItalic; }

	/**
	 * Gets whether the text is underlined.
	 * @return True when the text is underlined; otherwise, false.
	 */
	public boolean isUnderlined() { return this.isUnderlined; }

	/**
	 * Gets whether the text is strickenthrough.
	 * @return True when the text is strickenthrough; otherwise, false.
	 */
	public boolean isStrikenthrough() { return this.isStrickenthrough; }
	
	/**
	 * Creates a new instance of the {@link TextStyle} class.
	 * @param color The color.
	 * @param bold Whether the text is bold.
	 * @param italic Whether the text is italic.
	 * @param underlined Whether the text is underlined.
	 * @param strickenthrough Whether the text is strickenthrough.
	 */
	public TextStyle(RGB color, boolean bold, boolean italic, boolean underlined, boolean strickenthrough) {
		requireNotNull("color", color);
		
		this.color = color;
		this.isBold = bold;
		this.isItalic = italic;
		this.isUnderlined = underlined;
		this.isStrickenthrough = strickenthrough;
	}
	
	/**
	 * Creates a new instance of the {@link TextStyle} class.
	 * @param color The color.
	 */
	public TextStyle(RGB color) {
		this(color, false, false, false, false);
	}
	
	/**
	 * Creates a text attribute for this style.
	 * 
	 * @param colorManager The color manager.
	 * @return The text attribute.
	 */
	public TextAttribute createTextAttribute(ColorManager colorManager) {
		requireNotNull("colorManager", colorManager);
		
		Color foreground = colorManager.getColor(this.color);
		Color background = null;
		int style = SWT.NORMAL;
		style |= this.isBold ? SWT.BOLD : 0;
		style |= this.isItalic ? SWT.ITALIC : 0;
		style |= this.isUnderlined ? TextAttribute.UNDERLINE : 0;
		style |= this.isStrickenthrough ? TextAttribute.STRIKETHROUGH : 0;
		
		return new TextAttribute(foreground, background, style);
	}
}
