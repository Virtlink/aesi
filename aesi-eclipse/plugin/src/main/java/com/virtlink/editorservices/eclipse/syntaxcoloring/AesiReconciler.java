package com.virtlink.editorservices.eclipse.syntaxcoloring;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.presentation.PresentationReconciler;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class AesiReconciler extends PresentationReconciler {
	
	@Inject
	private static Provider<AesiSourceScanner> sourceScannerProvider;
	
	public AesiReconciler() {
        AesiSourceScanner scanner = sourceScannerProvider.get();
        SimpleDamageRepairer dr = new SimpleDamageRepairer(scanner);
        this.setDamager(dr, IDocument.DEFAULT_CONTENT_TYPE);
        this.setRepairer(dr, IDocument.DEFAULT_CONTENT_TYPE);
    }

}
