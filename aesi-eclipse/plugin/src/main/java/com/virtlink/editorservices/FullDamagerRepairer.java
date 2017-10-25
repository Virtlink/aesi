package com.virtlink.editorservices;

import org.eclipse.jface.text.DocumentEvent;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITypedRegion;
import org.eclipse.jface.text.Region;
import org.eclipse.jface.text.TextPresentation;
import org.eclipse.jface.text.presentation.IPresentationDamager;
import org.eclipse.jface.text.presentation.IPresentationRepairer;

public class FullDamagerRepairer implements IPresentationDamager, IPresentationRepairer {

	private IDocument document;
	
	@Override
	public void createPresentation(TextPresentation presentation, ITypedRegion damage) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setDocument(IDocument document) {
		this.document = document;
		
	}

	@Override
	public IRegion getDamageRegion(ITypedRegion partition, DocumentEvent event, boolean documentPartitioningChanged) {
		// Everything is damaged!
		return new Region(0, event.getDocument().getLength());
	}

}
