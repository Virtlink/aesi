package com.virtlink.aesi.eclipse.structureoutline;

import com.google.inject.Inject;

import com.google.inject.assistedinject.Assisted;
import com.virtlink.editorservices.IDocument;
import com.virtlink.editorservices.IProject;
import com.virtlink.editorservices.structureoutline.IStructureOutlineService;
import com.virtlink.editorservices.structureoutline.IStructureTreeNode;
import com.virtlink.editorservices.symbols.ISymbol;
import org.eclipse.jface.text.BadPositionCategoryException;
import org.eclipse.jface.text.DefaultPositionUpdater;
//import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IPositionUpdater;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.ui.texteditor.IDocumentProvider;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AesiContentProvider implements ITreeContentProvider {//implements ILazyTreeContentProvider {


	private final static String SEGMENTS = "__aesi_editor_segments";
	private IPositionUpdater positionUpdater = new DefaultPositionUpdater(SEGMENTS);
	private final IDocumentProvider documentProvider;
    private final IStructureOutlineService structureOutliner;
    private final Map<IStructureTreeNode, AesiStructureNode> nodes = new HashMap<>();

    @Inject
    public AesiContentProvider(@Assisted IDocumentProvider documentProvider,
                               IStructureOutlineService structureOutliner) {
    	this.documentProvider = documentProvider;
    	this.structureOutliner = structureOutliner;
    }

    @Override
    public Object[] getElements(Object element) {
        // TODO: read element, determine document?
        IProject project = null;
        IDocument document = null;
        List<? extends IStructureTreeNode> children = this.structureOutliner.getRootNodes(project, document, null);
        return children.stream().map(s -> new AesiStructureNode(project, document, s, null)).toArray(AesiStructureNode[]::new);
    }

    @Override
    public Object[] getChildren(Object element) {
        if (!(element instanceof AesiStructureNode))
            return new Object[0];
        AesiStructureNode parent = (AesiStructureNode)element;
        IProject project = parent.getProject();
        IDocument document = parent.getDocument();
        List<? extends IStructureTreeNode> children = this.structureOutliner.getChildNodes(project, document, parent.getNode(), null);
        return children.stream().map(s -> new AesiStructureNode(project, document, s, parent)).toArray(AesiStructureNode[]::new);
    }

    @Override
    @Nullable
    public Object getParent(Object element) {
        if (!(element instanceof AesiStructureNode))
            return null;
        AesiStructureNode node = (AesiStructureNode)element;
        return node.getParent();
    }

    @Override
    public boolean hasChildren(Object element) {
        if (!(element instanceof AesiStructureNode))
            return false;
        AesiStructureNode node = (AesiStructureNode)element;
        @Nullable Boolean b = this.structureOutliner.hasChildren(node.getProject(), node.getDocument(), node.getNode());
        return b == null || b;
    }

    @Override
    public void dispose() {
        // Do nothing.

    }

    @Override
    public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
        if (oldInput != null) {
            org.eclipse.jface.text.IDocument document = this.documentProvider.getDocument(oldInput);
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
            org.eclipse.jface.text.IDocument document = this.documentProvider.getDocument(newInput);
			if (document != null) {
				document.addPositionCategory(SEGMENTS);
				document.addPositionUpdater(this.positionUpdater);
			}
		}
    }
}
