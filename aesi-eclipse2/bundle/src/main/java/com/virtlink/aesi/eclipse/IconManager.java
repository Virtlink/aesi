package com.virtlink.aesi.eclipse;

import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

/**
 * Manages the icons.
 */
public class IconManager {

    private static final IconManager instance = new IconManager();
    public static IconManager getInstance() { return instance; }

    public Image getIcon() {
        Image icon = PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJ_FILE);
        return icon;
    }

}
