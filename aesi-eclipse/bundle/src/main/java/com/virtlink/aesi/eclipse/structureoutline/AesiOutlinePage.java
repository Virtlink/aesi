package com.virtlink.aesi.eclipse.structureoutline;

import com.virtlink.aesi.Span;
import com.virtlink.aesi.eclipse.editors.AesiDocumentManager;
import com.virtlink.aesi.eclipse.editors.AesiEditor;

import javax.annotation.Nullable;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
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

    public AesiOutlinePage(IDocumentProvider documentProvider, AesiEditor editor) {
        super();

        this.editor = editor;
        this.documentProvider = documentProvider;
    }
    
    @Override
    protected int getTreeStyle() {
    	return SWT.H_SCROLL | SWT.V_SCROLL;
    }

    @Override
    public void createControl(Composite parent) {
        super.createControl(parent);

        TreeViewer viewer = getTreeViewer();
        viewer.setContentProvider(new AesiContentProvider(this.documentProvider));
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
    		AesiStructureSymbol symbol = (AesiStructureSymbol)((IStructuredSelection) selection).getFirstElement();
    		Span location = symbol.getSymbol().getLocation();
    		try {
    			this.editor.setHighlightRange(location.getStartOffset(), location.getLength(), true);
    		} catch (IllegalArgumentException x) {
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
		TreeViewer viewer = getTreeViewer();

		if (viewer != null) {
			Control control= viewer.getControl();
			if (control != null && !control.isDisposed()) {
				control.setRedraw(false);
				viewer.setInput(this.input);
				viewer.expandAll();
				control.setRedraw(true);
			}
		}
	}
}
