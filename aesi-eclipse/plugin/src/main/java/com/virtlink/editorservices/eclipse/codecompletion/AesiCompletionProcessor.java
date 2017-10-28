package com.virtlink.editorservices.eclipse.codecompletion;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.virtlink.editorservices.codecompletion.ICodeCompletionService;
import com.virtlink.editorservices.codecompletion.ICompletionInfo;
import com.virtlink.editorservices.codecompletion.ICompletionProposal;
import com.virtlink.editorservices.eclipse.AesiIconManager;
import com.virtlink.editorservices.eclipse.Contract;
import com.virtlink.editorservices.eclipse.editor.ColorizationJob;
import com.virtlink.editorservices.eclipse.editor.IAesiEditor;
import com.virtlink.editorservices.eclipse.resources.EclipseResourceManager;

import org.eclipse.core.filebuffers.FileBuffers;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.contentassist.*;

import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.part.EditorPart;

import java.net.URI;
import java.util.List;

/**
 * A code completion processor for AESI languages.
 */
public class AesiCompletionProcessor implements IContentAssistProcessor {
	
	/**
	 * Factory for the {@link AesiCompletionProcessor} class.
	 */
	public static interface IFactory {
		/**
		 * Creates a new instance of the {@link AesiCompletionProcessor}.
		 * 
	     * @param editor The editor.
		 * @return The processor.
		 */
		AesiCompletionProcessor create(IAesiEditor editor);
	}
	
	private final IAesiEditor editor;
	private final ICodeCompletionService codeCompleter;
	private final EclipseResourceManager resourceManager;
	private final AesiIconManager iconManager;
	
	@Inject
	public AesiCompletionProcessor(
			@Assisted final IAesiEditor editor,
            final ICodeCompletionService codeCompleter,
            final EclipseResourceManager resourceManager,
            final AesiIconManager iconManager) {
		Contract.requireNotNull("editor", editor);
		Contract.requireNotNull("codeCompleter", codeCompleter);
		Contract.requireNotNull("resourceManager", resourceManager);
		Contract.requireNotNull("iconManager", iconManager);
		
		this.editor = editor;
		this.codeCompleter = codeCompleter;
		this.resourceManager = resourceManager;
		this.iconManager = iconManager;
	}
	
    @Override
    public org.eclipse.jface.text.contentassist.ICompletionProposal[] computeCompletionProposals(ITextViewer textViewer, int offset) {
    	final URI document = this.resourceManager.getUri(this.editor);
        ICompletionInfo completionInfo = this.codeCompleter.getCompletionInfo(document, offset, null);
        List<ICompletionProposal> proposals = completionInfo.getProposals();
        // TODO: Do something with the prefix.
        return proposals.stream().map(p -> toEclipseCompletionProposal(p, offset)).toArray(org.eclipse.jface.text.contentassist.ICompletionProposal[]::new);
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
    
    private org.eclipse.jface.text.contentassist.ICompletionProposal toEclipseCompletionProposal(ICompletionProposal proposal, long offset) {
        String replacementText = proposal.getInsertionText() != null ? proposal.getInsertionText() : proposal.getLabel();
        Image icon = this.iconManager.getIcon();
        long caret = proposal.getCaret() != null ? proposal.getCaret() : replacementText.length();
    	return new CompletionProposal(
                replacementText,
                (int)offset,
                0,
                (int)(offset + caret),
                icon,
                proposal.getLabel(),
                new ContextInformation(null, "Context display string", "Info display string"),
                proposal.getDescription()
        );
    }
}
