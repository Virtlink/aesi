package com.virtlink.aesi.eclipse.codecompletion;

import com.virtlink.aesi.IAesiDocument;
import com.virtlink.aesi.IAesiProject;
import com.virtlink.aesi.Location;
import com.virtlink.aesi.eclipse.AesiUtils;
import com.virtlink.aesi.eclipse.IconManager;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.contentassist.*;

import com.virtlink.aesi.codecompletion.DummyCodeCompleter;
import com.virtlink.editorservices.codecompletion.ICodeCompleter;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

import java.util.List;

/**
 * A code completion processor for AESI languages.
 */
public class AesiCompletionProcessor implements IContentAssistProcessor {
	
	private final ICodeCompleter codeCompleter = new DummyCodeCompleter();
	
    @Override
    public ICompletionProposal[] computeCompletionProposals(ITextViewer textViewer, int offset) {
    	// TODO: Determine project and document.
        IAesiDocument document = null;
        List<com.virtlink.editorservices.codecompletion.ICompletionProposal> proposals = this.codeCompleter.complete(document, offset, null);
        return proposals.stream().map(p -> toEclipseCompletionProposal(p, textViewer.getDocument(), offset)).toArray(ICompletionProposal[]::new);
    }

    @Override
    public IContextInformation[] computeContextInformation(ITextViewer textViewer, int offset) {
        return new IContextInformation[0];
    }

    @Override
    public char[] getCompletionProposalAutoActivationCharacters() {
        return new char[] { '.' };
    }

    @Override
    public char[] getContextInformationAutoActivationCharacters() {
        return new char[0];
    }

    @Override
    public String getErrorMessage() {
        return null;
    }

    @Override
    public IContextInformationValidator getContextInformationValidator() {
        return null;
    }
    
    private ICompletionProposal toEclipseCompletionProposal(com.virtlink.editorservices.codecompletion.ICompletionProposal proposal, IDocument document, int offset) {
        String replacementText = proposal.getInsertionText() != null ? proposal.getInsertionText() : proposal.getLabel();
        Image icon = IconManager.getInstance().getIcon();
    	return new CompletionProposal(
                replacementText,
                offset,
                0,
                offset + proposal.getCaret(),
                icon,
                proposal.getLabel(),
                new ContextInformation(null, "Context display string", "Info display string"),
                proposal.getDescription()
        );
    }
}
