package com.virtlink.aesi.eclipse.structureoutline;

import com.virtlink.aesi.eclipse.IconManager;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

public class AesiLabelProvider extends LabelProvider {
    @Override
    public String getText(Object element) {
        if (!(element instanceof AesiStructureSymbol))
            return null;

        AesiStructureSymbol symbol = (AesiStructureSymbol)element;
        return symbol.getSymbol().getName();
    }

    @Override
    public Image getImage(Object element) {
        if (!(element instanceof AesiStructureSymbol))
            return null;

        Image icon = IconManager.getInstance().getIcon();
        return icon;
    }
}
