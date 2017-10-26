package com.virtlink.editorservices.eclipse.syntaxcoloring;

import org.eclipse.jface.text.DocumentEvent;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITypedRegion;
import org.eclipse.jface.text.Region;
import org.eclipse.jface.text.rules.DefaultDamagerRepairer;
import org.eclipse.jface.text.rules.ITokenScanner;

public class SimpleDamageRepairer extends DefaultDamagerRepairer {

	public SimpleDamageRepairer(ITokenScanner scanner) {
		super(scanner);
	}
	
	@Override
	public IRegion getDamageRegion(ITypedRegion partition, DocumentEvent event, boolean documentPartitioningChanged) {
		// Everything is damaged!
		return new Region(0, event.getDocument().getLength());
	}

}
