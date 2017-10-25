package com.virtlink.aesi.eclipse.syntaxcoloring;

import org.eclipse.jface.text.*;
import org.eclipse.jface.text.presentation.IPresentationDamager;
import org.eclipse.jface.text.presentation.IPresentationReconciler;
import org.eclipse.jface.text.presentation.IPresentationReconcilerExtension;
import org.eclipse.jface.text.presentation.IPresentationRepairer;
import org.eclipse.swt.custom.StyleRange;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

/**
 * Base class for presentation reconcilers.
 */
public abstract class AbstractPresentationReconciler implements IPresentationReconciler, IPresentationReconcilerExtension {

    /**
     * Prefix of the name of the position category for tracking damage regions.
     */
    protected final static String TRACKED_PARTITION = "__reconciler_tracked_partition"; //$NON-NLS-1$

    /**
     * The presentation damagers.
     */
    private final Map<String, IPresentationDamager> damagers = new HashMap<>();
    /**
     * The presentation repairers.
     */
    private final Map<String, IPresentationRepairer> repairers = new HashMap<>();
    /**
     * The target text viewer.
     */
    private ITextViewer textViewer;
    /**
     * The internal event listener.
     */
    private final InternalListener internalListener = new InternalListener();
    /**
     * The name of the position category to track damage regions.
     */
    private String positionCategory;
    /**
     * The position updated for the damage regions' position category.
     */
    private IPositionUpdater positionUpdater;
    /**
     * The positions representing the damage regions.
     */
    @Nullable protected TypedPosition rememberedPosition;
    /**
     * Flag indicating the receipt of a partitioning changed notification.
     */
    protected boolean documentPartitioningChanged = false;
    /**
     * The range covering the changed partitioning.
     */
    @Nullable protected IRegion changedDocumentPartitions = null;
    /**
     * The partitioning used by this presentation reconciler.
     */
    private String partitioning;

    /**
     * Initializes a new instance of the {@link AbstractPresentationReconciler} class.
     */
    protected AbstractPresentationReconciler() {
        this.partitioning = IDocumentExtension3.DEFAULT_PARTITIONING;
        this.positionCategory = TRACKED_PARTITION + hashCode();
        this.positionUpdater = new DefaultPositionUpdater(this.positionCategory);
    }

    /**
     * Gets the document partitioning for this presentation reconciler.
     * @return
     */
    @Override
    public String getDocumentPartitioning() {
        return this.partitioning;
    }

    /**
     * Sets the document partitioning for this presentation reconciler.
     *
     * @param partitioning The partitioning.
     */
    public void setDocumentPartitioning(String partitioning) {
        this.partitioning = partitioning;
    }

    public boolean hasDamagers() {
        return this.damagers.isEmpty();
    }

    /**
     * Gets the presentation damager for the specified content type.
     *
     * @param contentType The content type.
     * @return The presentation damager; or {@code null} when not found.
     */
    @Override
    @Nullable
    public IPresentationDamager getDamager(String contentType) {
        return this.damagers.get(contentType);
    }

    /**
     * Sets the presentation damager for the specified content type.
     *
     * @param contentType The content type.
     * @param damager The presentation damager to register, or {@code null} to remove an existing one.
     */
    public void setDamager(String contentType, @Nullable IPresentationDamager damager) {
        if (damager == null)
            this.damagers.remove(contentType);
        else
            this.damagers.put(contentType, damager);
    }

    public boolean hasRepairers() {
        return this.repairers.isEmpty();
    }

    /**
     * Gets the presentation repairer for the specified content type.
     *
     * @param contentType The content type.
     * @return The presentation repairer; or {@code null} when not found.
     */
    @Override
    @Nullable
    public IPresentationRepairer getRepairer(String contentType) {
        return this.repairers.get(contentType);
    }

    /**
     * Sets the presentation repairer for the specified content type.
     *
     * @param contentType The content type.
     * @param repairer The presentation repairer to register, or {@code null} to remove an existing one.
     */
    public void setRepairer(String contentType, @Nullable IPresentationRepairer repairer) {
        if (repairer == null)
            this.repairers.remove(contentType);
        else
            this.repairers.put(contentType, repairer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void install(ITextViewer viewer) {
        this.textViewer = viewer;
        this.textViewer.addTextInputListener(internalListener);

        @Nullable IDocument document = viewer.getDocument();
        if (document != null)
            this.internalListener.inputDocumentChanged(null, document);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void uninstall() {
        this.textViewer.removeTextInputListener(this.internalListener);

        this.internalListener.inputDocumentAboutToBeChanged(this.textViewer.getDocument(), null);
    }

    /**
     * Assigns a document to all registered damagers and repairers.
     *
     * @param document The document.
     */
    private void assignDocumentToDamagersAndRepairers(IDocument document) {
        for (IPresentationDamager damager : this.damagers.values()) {
            damager.setDocument(document);
        }
        for (IPresentationRepairer repairer : this.repairers.values()) {
            repairer.setDocument(document);
        }
    }

    /**
     * Checks for the first and the last affected partition affected by a
     * document event and calls their damagers. Invalidates everything from the
     * start of the damage for the first partition until the end of the damage
     * for the last partition.
     *
     * @param e        the event describing the document change
     * @return the damaged caused by the change or <code>null</code> if
     * computing the partitioning failed
     * @since 3.0
     */
    @Nullable
    protected abstract IRegion getDamage(DocumentEvent e);

    /**
     * Processes the given damage.
     *
     * @param document The document to repair.
     * @param damagedRegion The damaged region; or null.
     */
    protected abstract void processDamage(IDocument document, @Nullable IRegion damagedRegion);

    /**
     * Returns the partition for the given offset in the given document.
     *
     * @param document the document
     * @param offset   the offset
     * @return the partition
     * @throws BadLocationException if offset is invalid in the given document
     * @since 3.0
     */
    protected ITypedRegion getPartition(IDocument document, int offset) throws BadLocationException {
        return TextUtilities.getPartition(document, getDocumentPartitioning(), offset, false);
    }


    protected void applyTextRegionCollection(TextPresentation presentation) {
        this.textViewer.changeTextPresentation(presentation, false);
    }

    /**
     * Internal listener class.
     */
    class InternalListener implements
            ITextInputListener, IDocumentListener, ITextListener,
            IDocumentPartitioningListener, IDocumentPartitioningListenerExtension, IDocumentPartitioningListenerExtension2 {

        /**
         * Set to <code>true</code> if between a document about to be changed and a changed event.
         */
        private boolean documentChanging = false;
        /**
         * The cached redraw state of the text viewer.
         *
         * @since 3.0
         */
        private boolean cachedRedrawState = true;

        @Override
        public void inputDocumentAboutToBeChanged(@Nullable IDocument oldDocument, @Nullable IDocument newDocument) {
            if (oldDocument == null)
                return;

            try {
                textViewer.removeTextListener(this);
                oldDocument.removeDocumentListener(this);
                oldDocument.removeDocumentPartitioningListener(this);

                oldDocument.removePositionUpdater(positionUpdater);
                oldDocument.removePositionCategory(positionCategory);
            } catch (BadPositionCategoryException x) {
                // Ignored.
            }
        }

        @Override
        public void inputDocumentChanged(@Nullable IDocument oldDocument, @Nullable IDocument newDocument) {

            documentChanging = false;
            cachedRedrawState = true;

            if (newDocument == null)
                return;

            newDocument.addPositionCategory(positionCategory);
            newDocument.addPositionUpdater(positionUpdater);

            newDocument.addDocumentPartitioningListener(this);
            newDocument.addDocumentListener(this);
            textViewer.addTextListener(this);

            assignDocumentToDamagersAndRepairers(newDocument);
            processDamage(newDocument, new Region(0, newDocument.getLength()));
        }

        @Override
        public void documentPartitioningChanged(DocumentPartitioningChangedEvent event) {
            IRegion changedRegion = event.getChangedRegion(getDocumentPartitioning());
            if (changedRegion != null)
                documentPartitioningChanged(event.getDocument(), changedRegion);
        }

        @Override
        public void documentPartitioningChanged(IDocument document) {
            documentPartitioningChanged(document, new Region(0, document.getLength()));
        }

        @Override
        public void documentPartitioningChanged(IDocument document, IRegion changedRegion) {
            if (!documentChanging && cachedRedrawState) {
                processDamage(document, new Region(changedRegion.getOffset(), changedRegion.getLength()));
            } else {
                documentPartitioningChanged = true;
                changedDocumentPartitions = changedRegion;
            }
        }

        @Override
        public void documentAboutToBeChanged(DocumentEvent event) {

            documentChanging = true;
            if (cachedRedrawState) {
                try {
                    int offset = event.getOffset() + event.getLength();
                    ITypedRegion region = getPartition(event.getDocument(), offset);
                    rememberedPosition = new TypedPosition(region);
                    event.getDocument().addPosition(positionCategory, rememberedPosition);
                } catch (BadLocationException | BadPositionCategoryException x) {
                    // Ignored.
                }
            }
        }

        @Override
        public void documentChanged(DocumentEvent event) {
            if (cachedRedrawState) {
                try {
                    event.getDocument().removePosition(positionCategory, rememberedPosition);
                } catch (BadPositionCategoryException x) {
                    // can not happen on input documents
                }
            }
            documentChanging = false;
        }

        @Override
        public void textChanged(TextEvent event) {

            cachedRedrawState = event.getViewerRedrawState();
            if (!cachedRedrawState)
                return;

            @Nullable DocumentEvent documentEvent = getDocumentEvent(event);

            if (documentEvent != null) {
                @Nullable IRegion damage = getDamage(documentEvent);
                @Nullable IDocument document = documentEvent.getDocument();

                if (damage != null && document != null)
                    processDamage(document, damage);
            }

            documentPartitioningChanged = false;
            changedDocumentPartitions = null;
        }

        @Nullable
        private DocumentEvent getDocumentEvent(TextEvent event) {
            @Nullable DocumentEvent documentEvent = event.getDocumentEvent();
            if (documentEvent == null) {
                @Nullable IDocument document = textViewer.getDocument();
                if (document != null) {

                    @Nullable IRegion region;
                    if (event.getOffset() == 0 && event.getLength() == 0 && event.getText() == null) {
                        region = new Region(0, document.getLength());
                    } else {
                        region = widgetRegion2ModelRegion(event);
                    }

                    if (region != null) {
                        try {
                            String text = document.get(region.getOffset(), region.getLength());
                            return new DocumentEvent(document, region.getOffset(), region.getLength(), text);
                        } catch (BadLocationException x) {
                            // Ignored.
                        }
                    }
                }
            }

            return documentEvent;
        }

        /**
         * Translates the given text event into the corresponding range of the viewer's document.
         */
        @Nullable
        private IRegion widgetRegion2ModelRegion(TextEvent event) {

            @Nullable String text = event.getText();
            int length = text == null ? 0 : text.length();

            if (textViewer instanceof ITextViewerExtension5) {
                ITextViewerExtension5 extension = (ITextViewerExtension5) textViewer;
                return extension.widgetRange2ModelRange(new Region(event.getOffset(), length));
            } else {
                IRegion visible = textViewer.getVisibleRegion();
                return new Region(event.getOffset() + visible.getOffset(), length);
            }
        }
    }

}
