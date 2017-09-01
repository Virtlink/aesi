package com.virtlink.aesi.codecompletion;

import java.util.List;

import com.virtlink.aesi.ICancellationToken;
import com.virtlink.aesi.IAesiDocument;
import com.virtlink.editorservices.codecompletion.ICompletionProposal;

import javax.annotation.Nullable;

/**
 * A code completer.
 */
public interface ICodeCompleterx {

	/**
	 * Returns completion proposals for the current caret position in the text in a document.
	 *
	 * The completion proposals must be returned in the order they must be displayed to the user.
	 * This function should filter the returned proposals by the prefix that the user has typed.
	 *
	 * The cancellation token is used to abort a long-running operation when the result is no longer needed.
	 * Implementations should listen to the cancellation event and abort, for example by throwing an
	 * exception or returning a dummy result. It is allowed to ignore the cancellation event, but
	 * this may negatively impact performance.
	 *
	 * @param document The document for which to provide completions.
	 * @param caretOffset The offset of the caret in the document.
	 * @param cancellationToken The cancellation token; or {@code null} when not supported.
	 * @return The list of tokens in the colorized document.
	 */
	List<ICompletionProposal> complete(
			IAesiDocument document,
			int caretOffset,
			@Nullable ICancellationToken cancellationToken);
}
