package com.virtlink.editorservices.eclipse.editor;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.text.TextPresentation;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.INavigationLocationProvider;
import org.eclipse.ui.IPersistableEditor;
import org.eclipse.ui.IReusableEditor;
import org.eclipse.ui.ISaveablesSource;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.ui.texteditor.ITextEditorExtension;
import org.eclipse.ui.texteditor.ITextEditorExtension2;
import org.eclipse.ui.texteditor.ITextEditorExtension3;
import org.eclipse.ui.texteditor.ITextEditorExtension4;
import org.eclipse.ui.texteditor.ITextEditorExtension5;
import org.eclipse.ui.texteditor.ITextEditorExtension6;

/**
 * An editor.
 */
public interface IAesiEditor extends IEditorPart, ITextEditor, IReusableEditor, ITextEditorExtension, ITextEditorExtension2, ITextEditorExtension3, ITextEditorExtension4, ITextEditorExtension5, ITextEditorExtension6, INavigationLocationProvider, ISaveablesSource, IPersistableEditor {
	
	/**
	 * Sets the editor text presentation.
	 * 
	 * @param presentation The text presentation.
	 * @param text The text that was presented. (Used to check whether the presentation is still valid.)
	 * @param monitor The progress monitor.
	 */
	void setPresentation(TextPresentation presentation, /*String text,*/ IProgressMonitor monitor);
	
}
