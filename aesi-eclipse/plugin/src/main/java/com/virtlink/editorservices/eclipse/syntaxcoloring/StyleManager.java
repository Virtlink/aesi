package com.virtlink.editorservices.eclipse.syntaxcoloring;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.Map.Entry;

import javax.annotation.Nullable;

import com.virtlink.editorservices.ScopeNames;
import org.eclipse.debug.internal.ui.ColorManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;

import com.virtlink.editorservices.Span;
import com.virtlink.editorservices.eclipse.Contract;

/**
 * Manages the syntax coloring styles.
 */
@SuppressWarnings("unused")
public final class StyleManager {
	
	/** The default token style name. */
	private static final String DEFAULT_STYLE_NAME = "text";
	/** Styles associated to token style names. */
	private final List<Entry<String, Style>> styles = Arrays.asList(
		// Put more specific scopes (longer prefixes) before more general scopes (shorter prefixes).
        // See https://manual.macromates.com/en/language_grammars for details about these scopes.
		createScopeStyle("source", getColor(SWT.COLOR_BLACK)),
		createScopeStyle("text", getColor(SWT.COLOR_BLACK)),
        // Comments
        createScopeStyle("comment", getColor(SWT.COLOR_DARK_BLUE)),
        // Entities
        createScopeStyle("entity", getColor(SWT.COLOR_DARK_RED)),
        // Invalid
        createScopeStyle("invalid.deprecated", getColor(SWT.COLOR_BLACK), null, false, false, false, true),
        createScopeStyle("invalid", getColor(SWT.COLOR_RED)),
        // Keywords
        createScopeStyle("keyword", getColor(SWT.COLOR_DARK_MAGENTA)),
        // Strings
        createScopeStyle("string", getColor(SWT.COLOR_BLUE)),
        // Variables
        createScopeStyle("variable", getColor(SWT.COLOR_DARK_YELLOW))
	);
	
	/**
	 * Gets the first style with the specified prefix.
	 * 
	 * @param scopes Tne scopes names.
	 * @return The style.
	 */
	public Style getStyle(ScopeNames scopes) {
		return this.styles.stream()
				.filter(e -> scopes.contains(e.getKey()))
				.map(Entry::getValue)
				.findFirst()
				.orElseGet( () -> getStyle(new ScopeNames(DEFAULT_STYLE_NAME)) );
	}
	
	/**
	 * Gets the default style.
	 * 
	 * @param span The span.
	 * @return The created style range.
	 */
	public StyleRange getDefaultStyleRange(Span span) {
		return getStyleRange(span, new ScopeNames(DEFAULT_STYLE_NAME));
	}
	
	/**
	 * Gets the style with the specified attributes.
	 * 
	 * @param span The span.
	 * @param scopes The scope names.
	 * @return The created style range.
	 */
	public StyleRange getStyleRange(final Span span, final ScopeNames scopes) {
		return getStyleRange(span, getStyle(scopes));
	}
	
	/**
	 * Gets the style with the specified attributes.
	 * 
	 * @param span The span.
	 * @param style The style.
	 * @return The created style range.
	 */
	public StyleRange getStyleRange(
			final Span span,
			final Style style
	) {
		final StyleRange styleRange = new StyleRange();
        styleRange.foreground = style.getForeground();
        styleRange.background = style.getBackground();
        if(style.isBold()) {
            styleRange.fontStyle |= SWT.BOLD;
        }
        if(style.isItalic()) {
            styleRange.fontStyle |= SWT.ITALIC;
        }
        styleRange.underline = style.hasUnderscore();
        styleRange.strikeout = style.hasStrikeout();

        styleRange.start = (int)span.getStartOffset();
        styleRange.length = (int)span.getLength();

        return styleRange;
	}

	/**
	 * Gets the specified SWT color.
	 * 
	 * @param color The color.
	 * @return The SWT color.
	 */
	public Color getColor(java.awt.Color color) {
		Contract.requireNotNull("color", color);
		
		return getColor(new RGB(color.getRed(), color.getGreen(), color.getBlue()));
	}
	
	/**
	 * Gets the specified SWT color.
	 * 
	 * @param color The RGB values.
	 * @return The SWT color.
	 */
	public Color getColor(RGB color) {
		Contract.requireNotNull("color", color);
		
		return ColorManager.getDefault().getColor(color);
	}

	/**
	 * Gets the specified SWT color.
	 * 
	 * @param id The color ID.
	 * @return The SWT color.
	 */
	public Color getColor(int id) {
		Contract.requireNotNull("id", id);
		
		return Display.getDefault().getSystemColor(id);
	}

	// Convenience functions:
	
	private static Entry<String, Style> entry(String name, Style style) {
		return new SimpleImmutableEntry<String, Style>(name, style);
	}
	
	private static Entry<String, Style> createScopeStyle(
			final String name,
			@Nullable final Color foreground,
			@Nullable final Color background,
			final boolean bold,
			final boolean italic,
			final boolean underscore,
			final boolean strikeout) {
		return entry(name, new Style(foreground, background, bold, italic, underscore, strikeout));
	}
	
	private static Entry<String, Style> createScopeStyle(
			final String name,
			@Nullable final Color foreground,
			@Nullable final Color background) {
		return createScopeStyle(name, foreground, background, false, false, false, false);
	}
	
	private static Entry<String, Style> createScopeStyle(
			final String name,
			@Nullable final Color foreground) {
		return createScopeStyle(name, foreground, null, false, false, false, false);
	}
	
}
