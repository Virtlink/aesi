package com.virtlink.aesi.eclipse.syntaxcoloring;

import org.eclipse.jface.text.*;
import org.eclipse.jface.text.presentation.IPresentationDamager;
import org.eclipse.jface.text.presentation.IPresentationReconciler;
import org.eclipse.jface.text.presentation.IPresentationReconcilerExtension;
import org.eclipse.jface.text.presentation.IPresentationRepairer;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.widgets.Display;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

/**
 * Implementation of the {@code IPresentationReconciler} that assumes
 * that its presentation repairer may take some time. The damagers however
 * should be quick, since they'll be executed on the main thread.
 */
public class AsyncPresentationReconciler extends AbstractPresentationReconciler {

    @Nullable
    protected IRegion getDamage(DocumentEvent e) {
        int length = e.getText() == null ? 0 : e.getText().length();

        if (!hasDamagers()) {
            length = Math.max(e.getLength(), length);
            length = Math.min(e.getDocument().getLength() - e.getOffset(), length);
            return new Region(e.getOffset(), length);
        }

        boolean isDeletion = length == 0;
        @Nullable IRegion damage = null;
        try {
            int offset = e.getOffset();
            if (isDeletion)
                offset = Math.max(0, offset - 1);
            ITypedRegion partition = getPartition(e.getDocument(), offset);
            @Nullable IPresentationDamager damager = getDamager(partition.getType());
            if (damager == null)
                return null;

            IRegion r = damager.getDamageRegion(partition, e, documentPartitioningChanged);

            int damageStart = r.getOffset();
            int damageEnd = getDamageEndOffset(e);

            if (changedDocumentPartitions != null) {
                damageStart = Math.min(damageStart, changedDocumentPartitions.getOffset());
                damageEnd = Math.max(damageEnd, changedDocumentPartitions.getOffset() + changedDocumentPartitions.getLength());
            }

            damage = damageEnd == -1 ? r : new Region(damageStart, damageEnd - damageStart);

        } catch (BadLocationException x) {
            // Ignored
        }

        return damage;
    }

    private int getDamageEndOffset(DocumentEvent e) throws BadLocationException {

        IDocument d = e.getDocument();

        int length = 0;
        if (e.getText() != null) {
            length = e.getText().length();
            if (length > 0)
                --length;
        }

        ITypedRegion partition = getPartition(d, e.getOffset() + length);
        int endOffset = partition.getOffset() + partition.getLength();
        if (endOffset == e.getOffset())
            return -1;

        int end = rememberedPosition == null ? -1 : rememberedPosition.getOffset() + rememberedPosition.getLength();
        if (endOffset < end && end < d.getLength())
            partition = getPartition(d, end);

        @Nullable IPresentationDamager damager = getDamager(partition.getType());
        if (damager == null)
            return -1;

        IRegion r = damager.getDamageRegion(partition, e, documentPartitioningChanged);

        return r.getOffset() + r.getLength();
    }

    @Override
    protected void processDamage(IDocument document, @Nullable IRegion damagedRegion) {
        final Display display = Display.getCurrent();

        new Thread(() -> {
            if (damagedRegion != null && damagedRegion.getLength() > 0) {
                @Nullable final TextPresentation presentation = createPresentation(damagedRegion, document);

                if (presentation != null) {
                    display.asyncExec(() -> applyTextRegionCollection(presentation));
                }
            }
        }).start();
    }

    @Nullable
    private TextPresentation createPresentation(IRegion damage, IDocument document) {
        try {
            if (!hasRepairers()) {
                TextPresentation presentation= new TextPresentation(damage, 100);
                presentation.setDefaultStyleRange(new StyleRange(damage.getOffset(), damage.getLength(), null, null));
                return presentation;
            }

            TextPresentation presentation= new TextPresentation(damage, 1000);

            ITypedRegion[] partitioning= TextUtilities.computePartitioning(document, getDocumentPartitioning(), damage.getOffset(), damage.getLength(), false);
            for (ITypedRegion r : partitioning) {
                @Nullable IPresentationRepairer repairer = getRepairer(r.getType());
                if (repairer != null)
                    repairer.createPresentation(presentation, r);
            }

            return presentation;

        } catch (BadLocationException x) {
            return null;
        }
    }
}
