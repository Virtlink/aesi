package com.virtlink.editorservices.eclipse;

import org.eclipse.jface.text.IRegion;

import com.virtlink.editorservices.Offset;
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
		
		return new Span(new Offset(region.getOffset()), new Offset(region.getOffset() + region.getLength()));
	}
}
