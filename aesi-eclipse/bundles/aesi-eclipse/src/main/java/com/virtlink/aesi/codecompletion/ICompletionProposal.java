package com.virtlink.aesi.codecompletion;

import com.virtlink.aesi.Location;

public interface ICompletionProposal {
	String getLabel();
	String getDescription();
	String getDocumentation();
	boolean isCaseSensitive();
	String getInsertionText();

	/**
	 * Gets the offset of the caret relative to the inserted proposal.
	 * @return The caret relative offset.
	 */
	int getCaretOffset();
}
