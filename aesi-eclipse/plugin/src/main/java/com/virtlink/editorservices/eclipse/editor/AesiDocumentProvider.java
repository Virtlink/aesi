package com.virtlink.editorservices.eclipse.editor;

import java.util.Iterator;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.text.IDocument;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.editors.text.TextFileDocumentProvider;
import org.eclipse.ui.texteditor.DocumentProviderRegistry;

import com.virtlink.editorservices.eclipse.AesiConstants;

/**
 * Creates the Eclipse documents for the AESI editor.
 */
public class AesiDocumentProvider extends TextFileDocumentProvider {
	
	public static AesiDocumentProvider getInstance() {
		return (AesiDocumentProvider)DocumentProviderRegistry.getDefault()
				.getDocumentProvider(AesiConstants.getFileExtension());
	}

	public AesiDocumentProvider() 
	{
	}
	
//	public IFile getFile(final IDocument document)
//    {
//        final IFile ret = null;
//        for ( final Iterator<Object> i = getConnectedElementsIterator(); 
//              i.hasNext() && ret == null;  )
//        {
//            final Object o = i.next();
//            if ( o instanceof IFileEditorInput )
//            {
//                final ElementInfo ei = getElementInfo( o );
//                get
//                if ( d == ei.fDocument )
//                {
//                    ret = ( ( IFileEditorInput )o ).getFile();
//                }
//            }
//        }
//        return ret;
//    }
}
