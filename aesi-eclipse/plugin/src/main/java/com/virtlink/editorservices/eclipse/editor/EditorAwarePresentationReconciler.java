package com.virtlink.editorservices.eclipse.editor;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.presentation.PresentationReconciler;
import org.eclipse.ui.texteditor.ITextEditor;

import com.virtlink.editorservices.eclipse.syntaxcoloring.SimpleDamageRepairer;

public class EditorAwarePresentationReconciler extends PresentationReconciler {

	private final ITextEditor editor;
	
	public EditorAwarePresentationReconciler(final ITextEditor editor) {
		this.editor = editor;
		
//        SimpleDamageRepairer dr = new SimpleDamageRepairer(scanner);
//        this.setDamager(dr, IDocument.DEFAULT_CONTENT_TYPE);
//        this.setRepairer(dr, IDocument.DEFAULT_CONTENT_TYPE);
	}
	
	
	
}
