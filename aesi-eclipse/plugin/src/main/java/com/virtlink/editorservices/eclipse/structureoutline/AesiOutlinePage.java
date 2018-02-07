package com.virtlink.editorservices.eclipse.structureoutline;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import javax.annotation.Nullable;

import com.virtlink.editorservices.Span;
import com.virtlink.editorservices.eclipse.editor.IAesiEditor;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.eclipse.ui.views.contentoutline.ContentOutlinePage;

public class AesiOutlinePage extends ContentOutlinePage {

	/**
	 * Factory for the {@link AesiOutlinePage} class.
	 */
	public static interface IFactory {
		/**
		 * Creates a new instance of the {@link AesiOutlinePage}.
		 * 
	     * @param documentProvider The document provider.
	     * @param editor The editor.
		 * @return The content provider.
		 */
		AesiOutlinePage create(IDocumentProvider documentProvider, IAesiEditor editor);
	}
	
	@Nullable private IEditorInput input;
    private final IAesiEditor editor;
    private final IDocumentProvider documentProvider;
    private final AesiTreeContentProvider.IFactory contentProviderFactory;
    private final AesiLabelProvider.IFactory labelProviderFactory;

    @Inject
    public AesiOutlinePage(
    		@Assisted final IDocumentProvider documentProvider,
			@Assisted final IAesiEditor editor,
			final AesiTreeContentProvider.IFactory contentProviderFactory,
			final AesiLabelProvider.IFactory labelProviderFactory) {
        super();

        this.editor = editor;
        this.documentProvider = documentProvider;
        this.contentProviderFactory = contentProviderFactory;
        this.labelProviderFactory = labelProviderFactory;
    }
    
    @Override
    protected int getTreeStyle() {
    	return SWT.H_SCROLL | SWT.V_SCROLL;
    }

    @Override
    public void createControl(Composite parent) {
        super.createControl(parent);

        TreeViewer viewer = getTreeViewer();
        viewer.setContentProvider(contentProviderFactory.create(this.documentProvider));
        viewer.setLabelProvider(labelProviderFactory.create());
        viewer.addSelectionChangedListener(this);
        
        update();
    }
    
    @Override
    public void selectionChanged(SelectionChangedEvent event) {
    	super.selectionChanged(event);

    	ISelection selection = event.getSelection();
    	if (selection.isEmpty())
    		this.editor.resetHighlightRange();
    	else {
    		AesiStructureNode node = (AesiStructureNode)((IStructuredSelection) selection).getFirstElement();
    		@Nullable Span range = node.getNode().getNameSpan();
    		if (range != null) {
				try {
					this.editor.setHighlightRange((int)range.getStartOffset(), (int)range.getLength(), true);
				} catch (IllegalArgumentException x) {
					this.editor.resetHighlightRange();
				}
			} else {
				this.editor.resetHighlightRange();
			}
    	}
    }

    /**
	 * Sets the input of the outline page
	 * 
	 * @param input the input of this outline page
	 */
	public void setInput(@Nullable IEditorInput input) {
		this.input = input;
		update();
	}
	
	/**
	 * Updates the outline page.
	 */
	public void update() {
		@Nullable TreeViewer viewer = getTreeViewer();

		if (viewer != null) {
			@Nullable Control control = viewer.getControl();
			if (control != null && !control.isDisposed()) {
				control.setRedraw(false);
				viewer.setInput(this.input);
				viewer.expandAll();
				control.setRedraw(true);
			}
		}
	}


}
