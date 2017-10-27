package com.virtlink.editorservices.eclipse.syntaxcoloring;

import javax.annotation.Nullable;

import org.eclipse.swt.graphics.Color;

/**
 * Specifies the style of code highlighting.
 */
public final class Style {
	
	/** The foreground color; or null. */
	@Nullable private final Color foreground;
	/** The background color; or null. */
	@Nullable private final Color background;
	/** Whether to make the text bold. */
	private final boolean bold;
	/** Whether to make the text italic. */
	private final boolean italic;
	/** Whether to underscore the text. */
	private final boolean underscore;
	/** Whether to strike-out the text. */
	private final boolean strikeout;

	/**
	 * Gets the foreground color; or null.
	 */
	public Color getForeground() { return this.foreground; }
	/**
	 * Gets the background color; or null.
	 */
	public Color getBackground() { return this.background; }
	/**
	 * Gets whether to make the text bold.
	 */
	public boolean isBold() { return this.bold; }
	/**
	 * Gets whether to make the text italic.
	 */
	public boolean isItalic() { return this.italic; }
	/**
	 * Gets whether to underscore the text.
	 */
	public boolean hasUnderscore() { return this.underscore; }
	/**
	 * Gets whether to strike-out the text.
	 */
	public boolean hasStrikeout() { return this.strikeout; }
	
	/**
	 * Initializes a new instance of the {@link Style} class.
	 * 
	 * @param foreground The foreground color; or null.
	 * @param background The background color; or null.
	 * @param bold Whether to make the text bold.
	 * @param italic Whether to make the text italic.
	 * @param underscore Whether to underscore the text.
	 * @param strikeout Whether to strike-out the text.
	 */
	public Style(
			@Nullable final Color foreground,
			@Nullable final Color background,
			final boolean bold,
			final boolean italic,
			final boolean underscore,
			final boolean strikeout
	) {
		this.foreground = foreground;
		this.background = background;
		this.bold = bold;
		this.italic = italic;
		this.underscore = underscore;
		this.strikeout = strikeout;
	}
	
}
