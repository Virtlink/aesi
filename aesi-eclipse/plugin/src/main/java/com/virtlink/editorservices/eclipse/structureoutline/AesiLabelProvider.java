package com.virtlink.editorservices.eclipse.structureoutline;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import com.google.inject.Inject;
import com.virtlink.editorservices.eclipse.AesiIconManager;
import com.virtlink.editorservices.eclipse.Contract;

public class AesiLabelProvider extends LabelProvider {
	
	/**
	 * Factory for the {@link AesiLabelProvider} class.
	 */
	public static interface IFactory {
		/**
		 * Creates a new instance of the {@link AesiLabelProvider}.
		 * 
		 * @return The label provider.
		 */
		AesiLabelProvider create();
	}
	
	private final AesiIconManager iconManager;
	
	@Inject
	public AesiLabelProvider(
			final AesiIconManager iconManager
	) {
		Contract.requireNotNull("iconManager", iconManager);
		this.iconManager = iconManager;
	}
	
    @Override
    public String getText(Object element) {
        if (!(element instanceof AesiStructureNode))
            return null;

        AesiStructureNode node = (AesiStructureNode)element;
        return node.getNode().getSymbol().getLabel();
    }

    @Override
    public Image getImage(Object element) {
        if (!(element instanceof AesiStructureNode))
            return null;

        Image icon = this.iconManager.getIcon();
        return icon;
    }
}
