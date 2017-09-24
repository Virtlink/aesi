package com.virtlink.aesi.eclipse.codecompletion;

import com.google.inject.Inject;
import com.virtlink.aesi.IAesiDocument;
import com.virtlink.aesi.IAesiProject;
import com.virtlink.aesi.Location;
import com.virtlink.aesi.eclipse.AesiUtils;
import com.virtlink.aesi.eclipse.IconManager;
import com.virtlink.editorservices.codecompletion.ICompletionInfo;
import com.virtlink.editorservices.codecompletion.ICompletionProposal;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.contentassist.*;

import com.virtlink.editorservices.codecompletion.ICodeCompleter;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

import java.util.List;

/**
 * A code completion processor for AESI languages.
 */
public class AesiCompletionProcessor implements IContentAssistProcessor {
	
	private final ICodeCompleter codeCompleter;
	
	@Inject
	public AesiCompletionProcessor(ICodeCompleter codeCompleter) {
		this.codeCompleter = codeCompleter;
	}
	
    @Override
    public org.eclipse.jface.text.contentassist.ICompletionProposal[] computeCompletionProposals(ITextViewer textViewer, int offset) {
    	// TODO: Determine project and document.
        com.virtlink.editorservices.IProject project = null;
        com.virtlink.editorservices.IDocument document = null;
        ICompletionInfo completionInfo = this.codeCompleter.complete(project, document, offset, null);
        List<ICompletionProposal> proposals = completionInfo.getProposals();
        // TODO: Do something with the prefix.
        return proposals.stream().map(p -> toEclipseCompletionProposal(p, textViewer.getDocument(), offset)).toArray(org.eclipse.jface.text.contentassist.ICompletionProposal[]::new);
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
    
    private org.eclipse.jface.text.contentassist.ICompletionProposal toEclipseCompletionProposal(com.virtlink.editorservices.codecompletion.ICompletionProposal proposal, IDocument document, int offset) {
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
