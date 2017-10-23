package com.virtlink.aesi.eclipse.structureoutline;

import com.virtlink.aesi.eclipse.IconManager;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

public class AesiLabelProvider extends LabelProvider {
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

        Image icon = IconManager.getInstance().getIcon();
        return icon;
    }
}
