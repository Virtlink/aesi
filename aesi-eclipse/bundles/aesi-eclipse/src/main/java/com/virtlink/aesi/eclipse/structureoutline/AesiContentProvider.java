package com.virtlink.aesi.eclipse.structureoutline;

import com.virtlink.aesi.IAesiDocument;
import com.virtlink.aesi.structureoutline.DummyStructureOutliner;
import com.virtlink.aesi.structureoutline.IStructureOutliner;
import com.virtlink.aesi.structureoutline.ISymbol;

import org.eclipse.jface.text.BadPositionCategoryException;
import org.eclipse.jface.text.DefaultPositionUpdater;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IPositionUpdater;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.ui.texteditor.IDocumentProvider;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AesiContentProvider implements ITreeContentProvider {//implements ILazyTreeContentProvider {


	private final static String SEGMENTS = "__aesi_editor_segments";
	private IPositionUpdater positionUpdater = new DefaultPositionUpdater(SEGMENTS);
	private final IDocumentProvider documentProvider;
    private final IStructureOutliner structureOutliner = new DummyStructureOutliner();
    private final Map<ISymbol, AesiStructureSymbol> symbols = new HashMap<>();
    
    public AesiContentProvider(IDocumentProvider documentProvider) {
    	this.documentProvider = documentProvider;
    }

    @Override
    public Object[] getElements(Object element) {
        // TODO: read element, determine document?
        IAesiDocument document = null;
        List<? extends ISymbol> children = this.structureOutliner.getRoots(document, null);
        return children.stream().map(s -> new AesiStructureSymbol(document, s, null)).toArray(AesiStructureSymbol[]::new);
    }

    @Override
    public Object[] getChildren(Object element) {
        if (!(element instanceof AesiStructureSymbol))
            return new Object[0];
        AesiStructureSymbol parent = (AesiStructureSymbol)element;
        IAesiDocument document = parent.getDocument();
        List<? extends ISymbol> children = this.structureOutliner.getChildren(document, parent.getSymbol(), null);
        return children.stream().map(s -> new AesiStructureSymbol(document, s, parent)).toArray(AesiStructureSymbol[]::new);
    }

    @Override
    public Object getParent(Object element) {
        if (!(element instanceof AesiStructureSymbol))
            return null;
        AesiStructureSymbol symbol = (AesiStructureSymbol)element;
        return symbol.getParent();
    }

    @Override
    public boolean hasChildren(Object element) {
        if (!(element instanceof AesiStructureSymbol))
            return false;
        AesiStructureSymbol symbol = (AesiStructureSymbol)element;
        return this.structureOutliner.hasChildren(symbol.getDocument(), symbol.getSymbol(), null);
    }

    @Override
    public void dispose() {
        // Do nothing.

    }

    @Override
    public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
        if (oldInput != null) {
			IDocument document = this.documentProvider.getDocument(oldInput);
			if (document != null) {
				try {
					document.removePositionCategory(SEGMENTS);
				} catch (BadPositionCategoryException x) {
					// Ignore.
				}
				document.removePositionUpdater(this.positionUpdater);
			}
        }
        
        // Content.
	
		if (newInput != null) {
			IDocument document= this.documentProvider.getDocument(newInput);
			if (document != null) {
				document.addPositionCategory(SEGMENTS);
				document.addPositionUpdater(this.positionUpdater);
			}
		}
    }
}
