package com.virtlink.editorservices.eclipse.editor;

import javax.annotation.Nullable;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.text.IDocument;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.texteditor.ITextEditor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.virtlink.editorservices.Span;

/**
 * Utility functions for working with editors.
 */
public final class EditorUtils {
	private EditorUtils() {}
	
	/** The logger. */
	private static Logger LOG = LoggerFactory.getLogger(EditorUtils.class);
	
	/**
	 * Opens a file in an editor.
	 * 
	 * @param file The file to open.
	 * @param span The highlighted range; or null.
	 * @param activate Whether to activate the editor.
	 */
	public static void openFileAsync(IFile file, @Nullable Span highlight, boolean activate) {
		// Run in the UI thread because we need to get the active workbench window and page.
		Display.getDefault().asyncExec(() -> {
			openFile(file, highlight, activate);
        });
	}
	
	/**
	 * Opens a file in an editor.
	 * 
	 * @param file The file to open.
	 * @param span The highlighted range; or null.
	 * @param activate Whether to activate the editor.
	 * @return The editor part.
	 */
	@Nullable
	public static IEditorPart openFile(IFile file, @Nullable Span highlight, boolean activate) {
		// Be sure to run this in the UI thread. Call openFileAsync() to be sure.
		final IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		IEditorPart editorPart = null;
		try {
			editorPart = IDE.openEditor(page, file, activate);
			if (highlight != null) {
				ITextEditor editor = getEditor(editorPart);
				if (editor != null) {
					IDocument document = editor.getDocumentProvider()
							.getDocument(editor.getEditorInput());
					editor.selectAndReveal((int)highlight.getStartOffset(), (int)highlight.getLength());
					page.activate(editor);
				} else {
					IMarker marker = file.createMarker("org.eclipse.core.resources.textmarker");
					marker.setAttribute("lineNumber", (int)highlight.getStartOffset());
					editorPart = IDE.openEditor(page, marker, activate);
					marker.delete();
				}
			}
		} catch(CoreException e) {
            LOG.error("Cannot open editor", e);
        }
		return editorPart;
	}
	
	public static ITextEditor getEditor(IEditorPart editorPart ) {
		if (editorPart instanceof ITextEditor)
			return (ITextEditor)editorPart;
		
		if (editorPart instanceof IAdaptable)
			return (ITextEditor)editorPart.getAdapter(ITextEditor.class);
		
		return null;
	}
}
