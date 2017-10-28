package com.virtlink.editorservices.eclipse;

import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.Region;

import com.virtlink.editorservices.Span;

public final class SpanUtils {

	private SpanUtils() {}
	
	/**
	 * Constructs a {@link Span} from a {@link IRegion}.
	 * 
	 * @param region The region.
	 * @return The span.
	 */
	public static Span fromRegion(IRegion region) {
		if (region == null) return null;
		
		return Span.fromLength(region.getOffset(), region.getLength());
	}

	/**
	 * Constructs a {@link IRegion} from a {@link Span}.
	 * 
	 * @param span The span.
	 * @return The region.
	 */
	public static IRegion toRegion(Span span) {
		if (span == null) return null;
		
		return new Region((int)span.getStartOffset(), (int)span.getLength());
	}
}
