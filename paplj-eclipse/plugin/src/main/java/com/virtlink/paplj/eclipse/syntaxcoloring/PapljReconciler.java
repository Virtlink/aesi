package com.virtlink.paplj.eclipse.syntaxcoloring;

import org.eclipse.jface.text.rules.ITokenScanner;

import com.virtlink.editorservices.eclipse.syntaxcoloring.AesiReconciler;
import com.virtlink.paplj.eclipse.PapljPlugin;

public class PapljReconciler extends AesiReconciler {
	
	public PapljReconciler() {
		super(
				PapljPlugin.getDefault().getInjector().getInstance(ITokenScanner.class)
		);
    }

}
