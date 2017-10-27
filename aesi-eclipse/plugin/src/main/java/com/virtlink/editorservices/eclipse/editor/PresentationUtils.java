package com.virtlink.editorservices.eclipse.editor;

import org.eclipse.jface.text.TextPresentation;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.widgets.Display;

import javax.annotation.Nullable;

import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.Region;

import com.virtlink.editorservices.syntaxcoloring.IToken;

/**
 * Utility functions for working with text editor presentations.
 */
public final class PresentationUtils {
	
	private PresentationUtils() {}
	
//	public static TextPresentation createPresentation(Iterable<IToken> tokens, @Nullable Display display) {
//		final TextPresentation presentation = new TextPresentation();
//		for (IToken token : tokens) {
//			final StyleRange styleRange = createStyleRange(token);
//			presentation.addStyleRange(styleRange);
//		}
//		
//		IRegion extent = presentation.getExtent();
//        if(extent == null) {
//            extent = new Region(0, 0);
//        }
//        final StyleRange defaultStyleRange = new StyleRange();
//        defaultStyleRange.start = extent.getOffset();
//        defaultStyleRange.length = extent.getLength();
//        defaultStyleRange.foreground = createColor(java.awt.Color.BLACK, display);
//        presentation.setDefaultStyleRange(defaultStyleRange);
//	}
//
//	private static StyleRange createStyleRange(IToken token) {
//		// TODO
//		return null;
//	}
}
