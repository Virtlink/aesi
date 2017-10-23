package com.virtlink.aesi.eclipse.editors;

import com.google.inject.assistedinject.Assisted;
import com.virtlink.aesi.eclipse.structureoutline.AesiOutlinePage;
import org.eclipse.ui.texteditor.IDocumentProvider;

public interface IOutlinePageFactory {

    AesiOutlinePage create(IDocumentProvider documentProvider, AesiEditor editor);
}
