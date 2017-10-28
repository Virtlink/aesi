package com.virtlink.editorservices.eclipse.actions;

import org.eclipse.ui.IWorkbenchSite;

public class OpenDeclarationAction extends SelectionAction {
	
	/** The action ID. */
	public static final String ID = ""; //$NON-NLS-1$

	public OpenDeclarationAction(IWorkbenchSite site) {
		super(site);
		setText(ActionMessages.OpenDeclaration_label);
		setDescription(ActionMessages.OpenDeclaration_description);
//		setActionDefinitionId(ActionMessages.OpenDeclaration_label);
	}

}
