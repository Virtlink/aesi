package com.virtlink.editorservices.eclipse.syntaxcoloring;

import java.util.Collection;

import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextPresentationListener;
import org.eclipse.jface.text.TextPresentation;
import org.eclipse.swt.custom.StyleRange;

import com.google.inject.Inject;

/**
 * Listens to changes to the text presentation, and merges our styles with
 * the styles contributed by others.
 */
public class PresentationMerger implements ITextPresentationListener {
	
	/** Our presentation. */
	private volatile TextPresentation sourcePresentation;
	/** Deep copy of the styles of our presentation, to prevent sharing with other ITextPresentationListeners. */
    private volatile Collection<StyleRange> styleRanges;
    
    @Inject public PresentationMerger() {}

    /**
     * Sets our presentation for the merger.
     * 
     * @param presentation Our presentation.
     */
    public void set(final TextPresentation presentation) {
        this.sourcePresentation = presentation;
        this.styleRanges = StyleUtils.cloneStyles(presentation);
    }

    /**
     * Unsets our presentation, which ignores our changes to the current presentation.
     */
    public void unset() {
        this.sourcePresentation = null;
        this.styleRanges = null;
    }

    /**
     * Applies our changes to the text presentation.
     * 
     * @param targetPresentation The text presentation to apply to.
     */
    @Override
    public void applyTextPresentation(final TextPresentation targetPresentation) {
        // No need to apply text presentation if source and target presentation are the same.
        if(sourcePresentation == null || targetPresentation == null || targetPresentation == sourcePresentation) {
            return;
        }

        final IRegion extent = targetPresentation.getExtent();
        final int start = extent.getOffset();
        final int end = start + extent.getLength();
        for(StyleRange styleRange : this.styleRanges) {
            final int styleRangeEnd = styleRange.start + styleRange.length;
            // Not allowed to change style ranges outside of extent.
            // Safe to skip since they will not be redrawn.
            if(styleRange.start < start || styleRangeEnd > end) {
                continue;
            }
            targetPresentation.mergeStyleRange(styleRange);
        }
    }
}
