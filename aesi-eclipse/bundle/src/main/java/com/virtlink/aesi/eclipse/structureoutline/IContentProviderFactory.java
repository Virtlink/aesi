package com.virtlink.aesi.eclipse.structureoutline;

import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.ui.texteditor.IDocumentProvider;

public interface IContentProviderFactory {

    IContentProvider create(IDocumentProvider documentProvider);
}
