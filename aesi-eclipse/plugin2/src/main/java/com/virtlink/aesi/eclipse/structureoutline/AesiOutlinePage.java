package com.virtlink.aesi.eclipse.structureoutline;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.virtlink.aesi.eclipse.editors.AesiEditor;

import javax.annotation.Nullable;

import com.virtlink.editorservices.Span;
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

	@Nullable private IEditorInput input;
    private final AesiEditor editor;
    private final IDocumentProvider documentProvider;
    private final IContentProviderFactory contentProviderFactory;

    @Inject
    public AesiOutlinePage(
    		@Assisted IDocumentProvider documentProvider,
			@Assisted AesiEditor editor,
			IContentProviderFactory contentProviderFactory) {
        super();

        this.editor = editor;
        this.documentProvider = documentProvider;
        this.contentProviderFactory = contentProviderFactory;
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
        viewer.setLabelProvider(new AesiLabelProvider());
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
    		@Nullable Span range = node.getNode().getSymbol().getNameRange();
    		if (range != null) {
				try {
					this.editor.setHighlightRange(range.getStart().getValue(), range.getLength(), true);
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
