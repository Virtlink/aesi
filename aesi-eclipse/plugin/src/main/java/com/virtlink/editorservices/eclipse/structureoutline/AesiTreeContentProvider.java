package com.virtlink.editorservices.eclipse.structureoutline;

import com.google.inject.Inject;

import com.google.inject.assistedinject.Assisted;
import com.virtlink.editorservices.NullCancellationToken;
import com.virtlink.editorservices.eclipse.codecompletion.AesiCompletionProcessor;
import com.virtlink.editorservices.eclipse.editor.IAesiEditor;
import com.virtlink.editorservices.eclipse.resources.EclipseResourceManager;
import com.virtlink.editorservices.structureoutline.IStructureOutlineService;
import com.virtlink.editorservices.structureoutline.IStructureTreeNode;
import com.virtlink.editorservices.symbols.ISymbol;
import org.eclipse.jface.text.BadPositionCategoryException;
import org.eclipse.jface.text.DefaultPositionUpdater;
import org.eclipse.jface.text.IPositionUpdater;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.texteditor.IDocumentProvider;

import javax.annotation.Nullable;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AesiTreeContentProvider implements ITreeContentProvider {//implements ILazyTreeContentProvider {
	
	/**
	 * Factory for the {@link IContentProvider} class.
	 */
	public static interface IFactory {
		/**
		 * Creates a new instance of the {@link IContentProvider}.
		 * 
	     * @param documentProvider The document provider.
		 * @return The content provider.
		 */
		AesiTreeContentProvider create(IDocumentProvider documentProvider);
	}

	private final static String SEGMENTS = "__aesi_editor_segments";
	private final IPositionUpdater positionUpdater = new DefaultPositionUpdater(SEGMENTS);
	private final IDocumentProvider documentProvider;
	private final IStructureOutlineService structureOutliner;
    private final EclipseResourceManager resourceManager;

    @Inject
    public AesiTreeContentProvider(
    		@Assisted final IDocumentProvider documentProvider,
    		final IStructureOutlineService structureOutliner,
    		final EclipseResourceManager resourceManager
    ) {
    	this.documentProvider = documentProvider;
    	this.structureOutliner = structureOutliner;
    	this.resourceManager = resourceManager;
    }

    @Override
    public Object[] getElements(Object element) {
        URI document = this.resourceManager.getUri((IEditorInput)element);
        List<? extends IStructureTreeNode> children = this.structureOutliner.getRootNodes(document, NullCancellationToken.INSTANCE);
        return children.stream().map(s -> new AesiStructureNode(document, s, null)).toArray(AesiStructureNode[]::new);
    }

    @Override
    public Object[] getChildren(Object element) {
        if (!(element instanceof AesiStructureNode))
            return new Object[0];
        AesiStructureNode parent = (AesiStructureNode)element;
        URI document = parent.getDocumentUri();
        List<? extends IStructureTreeNode> children = this.structureOutliner.getChildNodes(document, parent.getNode(), NullCancellationToken.INSTANCE);
        return children.stream().map(s -> new AesiStructureNode(document, s, parent)).toArray(AesiStructureNode[]::new);
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
        @Nullable Boolean b = this.structureOutliner.hasChildren(node.getDocumentUri(), node.getNode());
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
