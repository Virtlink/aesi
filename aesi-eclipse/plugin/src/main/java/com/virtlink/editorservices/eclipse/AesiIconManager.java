package com.virtlink.editorservices.eclipse;

import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

/**
 * Manages the icons.
 */
public class AesiIconManager {

//    private static final AesiIconManager instance = new AesiIconManager();
//    public static AesiIconManager getInstance() { return instance; }

    public Image getIcon() {
        Image icon = PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJ_FILE);
        return icon;
    }

}