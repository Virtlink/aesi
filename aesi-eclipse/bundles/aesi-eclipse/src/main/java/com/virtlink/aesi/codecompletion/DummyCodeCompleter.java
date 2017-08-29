package com.virtlink.aesi.codecompletion;

import java.util.ArrayList;
import java.util.List;

import com.virtlink.aesi.IAesiDocument;
import com.virtlink.aesi.ICancellationToken;
import com.virtlink.aesi.IAesiProject;
import com.virtlink.aesi.Location;

import javax.annotation.Nullable;

public class DummyCodeCompleter implements ICodeCompleter {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<ICompletionProposal> complete(
			IAesiDocument document,
			int caretOffset,
			@Nullable ICancellationToken cancellationToken) {
		List<ICompletionProposal> proposals = new ArrayList<>();
		proposals.add(new CompletionProposal("Proposal1", "Description", "Documentation", false, "insertion1", 0));
		proposals.add(new CompletionProposal("Another prposal", "Description 2", "Documentation 2", false, "another2", 0));
		return proposals;
	}
}
