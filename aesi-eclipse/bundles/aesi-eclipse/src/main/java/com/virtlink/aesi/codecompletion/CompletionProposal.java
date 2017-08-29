package com.virtlink.aesi.codecompletion;

import com.virtlink.aesi.Location;

/**
 * A completion proposal.
 */
public class CompletionProposal implements ICompletionProposal {

	private final String label;
	private final String description;
	private final String documentation;
	private final boolean caseSensitive;
	private final String insertionText;
	private final int caretOffset;
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getLabel() { return this.label; }

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getDescription() { return this.description; }

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getDocumentation() { return this.documentation; }

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isCaseSensitive() { return this.caseSensitive; }

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getInsertionText() { return this.insertionText; }

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getCaretOffset() { return this.caretOffset; }

	/**
	 * Initializes a new instance of the {@link CompletionProposal} class.
	 * 
	 * @param label The label of the proposal.
	 * @param description The description of the proposal; or null.
	 * @param documentation The documentation of the proposal; or null.
	 * @param caseSensitive Whether the filter text is case sensitive.
	 * @param insertionText The text to insert; or null to insert the label.
	 * @param caretOffset The caret offset; or null to set the caret after the inserted text.
	 */
	public CompletionProposal(String label, String description, String documentation, boolean caseSensitive, String insertionText, int caretOffset) {
		this.label = label;
		this.description = description;
		this.documentation = documentation;
		this.caseSensitive = caseSensitive;
		this.insertionText = insertionText;
		this.caretOffset = caretOffset;
	}
	
	@Override
	public String toString() {
		return this.label;
	}

}
