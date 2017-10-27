package com.virtlink.editorservices.eclipse.syntaxcoloring;

import java.util.Collection;
import java.util.Iterator;

import org.eclipse.jface.text.TextPresentation;
import org.eclipse.swt.custom.StyleRange;

import com.google.common.collect.Lists;

public final class StyleUtils {
	
	private StyleUtils() {}

    /**
     * Creates deep copies of style ranges in given text presentation.
     * 
     * @param presentation The text presentation whose style ranges to copy.
     * @return A collection of copies of the style ranges in the presentation.
     */
    public static Collection<StyleRange> cloneStyles(TextPresentation presentation) {
		if (presentation == null) {
			throw new IllegalArgumentException("presentation must not be null");
		}
		
        final Collection<StyleRange> styleRanges = Lists.newLinkedList();
        for(Iterator<StyleRange> iter = presentation.getNonDefaultStyleRangeIterator(); iter.hasNext();) {
            final StyleRange styleRange = iter.next();
            styleRanges.add(clone(styleRange));
        }
        return styleRanges;
    }
    
    /**
     * Creates a deep copy of given style range.
     * 
     * @param styleRange The style range to copy.
     * @return Deep copy of the given style range.
     */
	public static StyleRange clone(StyleRange styleRange) {
		if (styleRange == null) {
			throw new IllegalArgumentException("styleRange must not be null");
		}
		
        final StyleRange styleRangeCopy = new StyleRange(styleRange);
        styleRangeCopy.start = styleRange.start;
        styleRangeCopy.length = styleRange.length;
        styleRangeCopy.fontStyle = styleRange.fontStyle;
        return styleRangeCopy;
    }

}
